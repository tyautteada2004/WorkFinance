package com.tyaut.workfinance.domain.usecase

import com.tyaut.workfinance.data.db.entity.WorkplaceEntity
import com.tyaut.workfinance.domain.enums.WageType
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

class CalculateShiftWageUseCase @Inject constructor() {

    /** 深夜時間帯（22時〜翌5時）の境界 */
    private val NIGHT_START = LocalTime.of(22, 0)
    private val NIGHT_END = LocalTime.of(5, 0)

    /**
     * 勤務先設定と実働時間から賃金（円）を計算。
     * 時給制の場合のみ計算。月給制は呼び出し元で月額/営業日数で算出する。
     */
    operator fun invoke(
        workplace: WorkplaceEntity,
        start: LocalDateTime,
        end: LocalDateTime,
        breakMinutes: Int = 0
    ): Long {
        if (workplace.wageType == WageType.MONTHLY) {
            // 月給制: 固定給のため0を返し、Repositoryから月額を直接使う
            return workplace.monthlyWage ?: 0L
        }

        val hourlyWage = workplace.hourlyWage ?: return 0L
        val totalMinutes = java.time.Duration.between(start, end).toMinutes() - breakMinutes
        if (totalMinutes <= 0) return 0L

        // 通常時間と深夜時間を分離して計算
        val normalMinutes = BigDecimal(calculateNormalMinutes(start, end, totalMinutes))
        val nightMinutes = BigDecimal(totalMinutes) - normalMinutes

        val normalPay = BigDecimal(hourlyWage) * normalMinutes / BigDecimal(60)
        val nightPay = BigDecimal(hourlyWage) * BigDecimal(workplace.nightPremiumRate) * nightMinutes / BigDecimal(60)

        val total = (normalPay + nightPay).setScale(0, workplace.roundingRule.roundingMode)
        return total.toLong()
    }

    private fun calculateNormalMinutes(start: LocalDateTime, end: LocalDateTime, totalMinutes: Long): Long {
        // 簡易実装: 22時〜翌5時を深夜として正確に計算するには区間分割が必要
        // ここでは全区間を通常とし、深夜判定は分単位でループ（最大24*60=1440回）
        var normalMinutes = 0L
        var current = start
        val step = java.time.Duration.ofMinutes(1)
        repeat(totalMinutes.toInt()) {
            val t = current.toLocalTime()
            val isNight = t >= NIGHT_START || t < NIGHT_END
            if (!isNight) normalMinutes++
            current = current.plus(step)
        }
        return normalMinutes
    }
}
