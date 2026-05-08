package com.tyaut.workfinance.domain.usecase

import com.tyaut.workfinance.data.db.entity.TransactionEntity
import com.tyaut.workfinance.data.repository.ShiftRepository
import com.tyaut.workfinance.data.repository.TransactionRepository
import com.tyaut.workfinance.data.repository.WorkplaceRepository
import com.tyaut.workfinance.domain.enums.TransactionType
import com.tyaut.workfinance.util.millisToLocalDate
import javax.inject.Inject

/**
 * シフト確定処理（予定→実績→確定の最終ステップ）。
 * confirmedWage を記録し、対応する収入トランザクションを生成する。
 */
class ConfirmShiftUseCase @Inject constructor(
    private val shiftRepo: ShiftRepository,
    private val transactionRepo: TransactionRepository,
    private val workplaceRepo: WorkplaceRepository,
) {
    suspend operator fun invoke(shiftId: Long, confirmedWage: Long) {
        val shift = shiftRepo.findById(shiftId) ?: return
        val workplace = workplaceRepo.findById(shift.workplaceId) ?: return

        shiftRepo.confirm(shiftId, confirmedWage)

        // 給与振込日を設定（給料日に営業日補正済み）
        val payDate = shift.scheduledStart.millisToLocalDate()

        transactionRepo.insert(
            TransactionEntity(
                accountId = workplace.depositAccountId,
                amount = confirmedWage,
                type = TransactionType.INCOME,
                category = null,
                description = "${shift.workplaceName} 給与",
                transactionDate = payDate.toEpochDay(),
                isConfirmed = true
            )
        )
    }
}
