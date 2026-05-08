package com.tyaut.workfinance.util

import com.tyaut.workfinance.domain.enums.BusinessDayRule
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

object BusinessDayCalculator {

    /**
     * 指定日が休業日（土日祝）なら businessDayRule に従って補正した営業日を返す。
     * 祝日は簡易実装（主要な祝日のみ）。完全対応は祝日APIとの連携を推奨。
     */
    fun adjust(date: LocalDate, rule: BusinessDayRule?): LocalDate {
        if (rule == null || isBusinessDay(date)) return date
        return when (rule) {
            BusinessDayRule.PREVIOUS -> prevBusinessDay(date)
            BusinessDayRule.NEXT -> nextBusinessDay(date)
        }
    }

    /** 年・月・日から対象月の補正済み日付を生成（月末超は月末に丸める） */
    fun resolveDate(year: Int, month: Month, dayOfMonth: Int, rule: BusinessDayRule?): LocalDate {
        val lastDay = LocalDate.of(year, month, 1).lengthOfMonth()
        val clamped = minOf(dayOfMonth, lastDay)
        val date = LocalDate.of(year, month, clamped)
        return adjust(date, rule)
    }

    fun isBusinessDay(date: LocalDate): Boolean =
        date.dayOfWeek != DayOfWeek.SATURDAY &&
        date.dayOfWeek != DayOfWeek.SUNDAY &&
        !isJapaneseHoliday(date)

    private fun prevBusinessDay(date: LocalDate): LocalDate {
        var d = date.minusDays(1)
        while (!isBusinessDay(d)) d = d.minusDays(1)
        return d
    }

    private fun nextBusinessDay(date: LocalDate): LocalDate {
        var d = date.plusDays(1)
        while (!isBusinessDay(d)) d = d.plusDays(1)
        return d
    }

    /** 主要な固定祝日のみ。振替休日・春分秋分は未対応（将来的に祝日DBで拡張） */
    private fun isJapaneseHoliday(date: LocalDate): Boolean {
        val m = date.monthValue
        val d = date.dayOfMonth
        return when {
            m == 1 && d == 1 -> true   // 元日
            m == 1 && d == 2 -> date.dayOfWeek == DayOfWeek.MONDAY // 振替（簡易）
            m == 2 && d == 11 -> true  // 建国記念日
            m == 2 && d == 23 -> true  // 天皇誕生日
            m == 4 && d == 29 -> true  // 昭和の日
            m == 5 && d == 3 -> true   // 憲法記念日
            m == 5 && d == 4 -> true   // みどりの日
            m == 5 && d == 5 -> true   // こどもの日
            m == 8 && d == 11 -> true  // 山の日
            m == 11 && d == 3 -> true  // 文化の日
            m == 11 && d == 23 -> true // 勤労感謝の日
            else -> false
        }
    }
}
