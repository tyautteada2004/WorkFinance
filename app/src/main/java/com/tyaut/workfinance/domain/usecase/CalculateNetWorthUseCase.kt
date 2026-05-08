package com.tyaut.workfinance.domain.usecase

import com.tyaut.workfinance.data.repository.AccountRepository
import com.tyaut.workfinance.data.repository.TransactionRepository
import com.tyaut.workfinance.domain.model.NetWorthSnapshot
import com.tyaut.workfinance.domain.enums.AccountType
import com.tyaut.workfinance.util.toLocalDate
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import javax.inject.Inject

class CalculateNetWorthUseCase @Inject constructor(
    private val accountRepo: AccountRepository,
    private val transactionRepo: TransactionRepository,
) {
    suspend operator fun invoke(asOf: LocalDate = LocalDate.now()): NetWorthSnapshot {
        val accounts = accountRepo.observeAll().first()
        val epochDay = asOf.toEpochDay()

        var totalAssets = 0L
        var totalLiabilities = 0L

        for (account in accounts) {
            val delta = transactionRepo.sumAmountUpTo(account.id, epochDay)
            val balance = account.initialBalance + delta

            when {
                account.type.isAsset -> totalAssets += balance
                // クレカ・借入: 残高（負値）が負債。絶対値を負債合計に加算
                account.type.isLiability -> totalLiabilities += -balance
            }
        }

        return NetWorthSnapshot(totalAssets, totalLiabilities)
    }
}
