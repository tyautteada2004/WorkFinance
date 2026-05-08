package com.tyaut.workfinance.domain.usecase

import com.tyaut.workfinance.data.repository.AccountRepository
import com.tyaut.workfinance.data.repository.TransactionRepository
import com.tyaut.workfinance.util.toYearMonth
import java.time.YearMonth
import javax.inject.Inject

/**
 * クレカ確定処理。
 * ポータル確認後に呼び出し、支払月を前後1ヶ月の範囲で手動選択・確定する。
 * これにより月跨ぎ問題を完全解消し、予測残高を即座に再計算する。
 */
class ConfirmCreditCardUseCase @Inject constructor(
    private val transactionRepo: TransactionRepository,
    private val accountRepo: AccountRepository,
) {
    /**
     * @param transactionId 確定する取引ID
     * @param selectedPaymentMonth 実際の支払月（自動計算の前後1ヶ月から選択）
     */
    suspend operator fun invoke(transactionId: Long, selectedPaymentMonth: YearMonth) {
        transactionRepo.confirmCreditCard(
            id = transactionId,
            paymentMonth = selectedPaymentMonth.toString()
        )
    }

    /** 自動計算の支払月と前後1ヶ月の選択肢を返す */
    fun paymentMonthOptions(calculatedMonth: YearMonth): List<YearMonth> = listOf(
        calculatedMonth.minusMonths(1),
        calculatedMonth,
        calculatedMonth.plusMonths(1)
    )
}
