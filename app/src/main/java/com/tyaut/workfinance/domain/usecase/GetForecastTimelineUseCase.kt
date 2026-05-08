package com.tyaut.workfinance.domain.usecase

import com.tyaut.workfinance.data.db.entity.AccountEntity
import com.tyaut.workfinance.data.repository.AccountRepository
import com.tyaut.workfinance.data.repository.FixedExpenseRepository
import com.tyaut.workfinance.data.repository.ShiftRepository
import com.tyaut.workfinance.data.repository.TransactionRepository
import com.tyaut.workfinance.data.repository.WorkplaceRepository
import com.tyaut.workfinance.domain.enums.AccountType
import com.tyaut.workfinance.domain.enums.BusinessDayRule
import com.tyaut.workfinance.domain.model.ForecastEntry
import com.tyaut.workfinance.domain.model.ForecastEvent
import com.tyaut.workfinance.util.BusinessDayCalculator
import com.tyaut.workfinance.util.millisToLocalDate
import com.tyaut.workfinance.util.toKey
import com.tyaut.workfinance.util.toYearMonth
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class GetForecastTimelineUseCase @Inject constructor(
    private val accountRepo: AccountRepository,
    private val transactionRepo: TransactionRepository,
    private val fixedExpenseRepo: FixedExpenseRepository,
    private val shiftRepo: ShiftRepository,
    private val workplaceRepo: WorkplaceRepository,
) {

    /**
     * today から daysAhead 日分の予測残高タイムラインを生成。
     * 手元資金（CASH + BANK）合計を起点に、以下を加算:
     *  - 固定収支（サブスク等）
     *  - クレカ引落（未確定分を支払月の引落日にまとめて計上）
     *  - シフト給料（予定/実績ベースを給料日にまとめて計上）
     */
    suspend operator fun invoke(today: LocalDate, daysAhead: Int = 60): List<ForecastEntry> {
        val allAccounts = accountRepo.observeAll().first()
        val cashBankAccounts = allAccounts.filter { it.type == AccountType.CASH || it.type == AccountType.BANK }
        val creditCardAccounts = allAccounts.filter { it.type == AccountType.CREDIT_CARD }

        var runningBalance = cashBankAccounts.sumOf { account ->
            account.initialBalance + transactionRepo.sumAmountUpTo(account.id, today.toEpochDay())
        }

        val forecastEnd = today.plusDays(daysAhead.toLong())
        val cardPaymentsByDate = buildCardPaymentsMap(today, forecastEnd, creditCardAccounts.associateBy { it.id })
        val shiftPaysByDate = buildShiftPaymentsMap(today, forecastEnd)

        val entries = mutableListOf<ForecastEntry>()

        for (dayOffset in 0..daysAhead) {
            val date = today.plusDays(dayOffset.toLong())
            val events = mutableListOf<ForecastEvent>()
            var inflow = 0L
            var outflow = 0L

            // 固定収支（当日が補正済み発生日と一致するもの）
            for (fe in fixedExpenseRepo.getActiveOnDate(date.toEpochDay())) {
                val resolved = BusinessDayCalculator.resolveDate(
                    date.year, date.month, fe.dayOfMonth, fe.businessDayRule
                )
                if (resolved == date) {
                    events.add(ForecastEvent(fe.name, fe.amount, false))
                    if (fe.amount >= 0) inflow += fe.amount else outflow += fe.amount
                }
            }

            // クレカ引落（amount はマイナス値）
            cardPaymentsByDate[date]?.forEach { (cardName, amount) ->
                events.add(ForecastEvent("${cardName}引落", amount, false))
                outflow += amount
            }

            // シフト給料（amount はプラス値）
            shiftPaysByDate[date]?.forEach { (workplaceName, amount) ->
                events.add(ForecastEvent("${workplaceName}給料", amount, false))
                inflow += amount
            }

            runningBalance += inflow + outflow
            entries.add(ForecastEntry(date, runningBalance, inflow, -outflow, events))
        }

        return entries
    }

    /**
     * 未確定クレカ取引を支払月の引落日にグループ化する。
     * creditCardPaymentMonth が設定済みかつ isConfirmed=false のものが対象。
     */
    private suspend fun buildCardPaymentsMap(
        today: LocalDate,
        forecastEnd: LocalDate,
        cardAccountsById: Map<Long, AccountEntity>
    ): Map<LocalDate, List<Pair<String, Long>>> {
        val fromYm = YearMonth.from(today).toKey()
        val futureTxns = transactionRepo.getFutureCardTransactions(fromYm)
            .filter { !it.isConfirmed && it.creditCardPaymentMonth != null }

        val result = mutableMapOf<LocalDate, MutableList<Pair<String, Long>>>()

        futureTxns
            .groupBy { it.accountId to it.creditCardPaymentMonth!! }
            .forEach { (key, txns) ->
                val (accountId, paymentMonthStr) = key
                val card = cardAccountsById[accountId] ?: return@forEach
                val payDay = card.paymentDay ?: return@forEach
                val ym = paymentMonthStr.toYearMonth()
                val payDate = BusinessDayCalculator.resolveDate(
                    ym.year, ym.month, payDay, card.businessDayRule
                )
                if (payDate in today..forecastEnd) {
                    val total = txns.sumOf { it.amount }
                    result.getOrPut(payDate) { mutableListOf() }.add(card.name to total)
                }
            }

        return result
    }

    /**
     * 未確定シフト（SCHEDULED / ACTUAL）の期待賃金を給料日ごとに合算する。
     * 同一勤務先の同一給料日分はひとつのイベントにまとめる。
     */
    private suspend fun buildShiftPaymentsMap(
        today: LocalDate,
        forecastEnd: LocalDate
    ): Map<LocalDate, List<Pair<String, Long>>> {
        val workplaceMap = workplaceRepo.observeAll().first().associateBy { it.id }
        val unconfirmedShifts = shiftRepo.getUnconfirmed()

        val wageByKey = mutableMapOf<Pair<Long, LocalDate>, Long>()
        val nameByKey = mutableMapOf<Pair<Long, LocalDate>, String>()

        for (shift in unconfirmedShifts) {
            val workplace = workplaceMap[shift.workplaceId] ?: continue
            val shiftDate = shift.scheduledStart.millisToLocalDate()
            val payDate = resolvePayDay(shiftDate, workplace.payDay, workplace.payDayBusinessDayRule)

            if (payDate in today..forecastEnd) {
                val wage = shift.actualWage ?: shift.scheduledWage ?: 0L
                val key = workplace.id to payDate
                wageByKey[key] = (wageByKey[key] ?: 0L) + wage
                nameByKey[key] = workplace.name
            }
        }

        val result = mutableMapOf<LocalDate, MutableList<Pair<String, Long>>>()
        for ((key, total) in wageByKey) {
            val (_, payDate) = key
            val name = nameByKey[key] ?: continue
            result.getOrPut(payDate) { mutableListOf() }.add(name to total)
        }
        return result
    }

    /**
     * シフト日以降の最初の給料日を返す。
     * シフト日当月の給料日がシフト日以降なら当月、そうでなければ翌月。
     */
    private fun resolvePayDay(shiftDate: LocalDate, payDay: Int, rule: BusinessDayRule?): LocalDate {
        val sameMonth = BusinessDayCalculator.resolveDate(shiftDate.year, shiftDate.month, payDay, rule)
        return if (!sameMonth.isBefore(shiftDate)) sameMonth
               else {
                   val next = shiftDate.plusMonths(1)
                   BusinessDayCalculator.resolveDate(next.year, next.month, payDay, rule)
               }
    }
}
