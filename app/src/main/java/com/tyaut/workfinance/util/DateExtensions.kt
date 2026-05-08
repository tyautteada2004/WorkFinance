package com.tyaut.workfinance.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val ZONE = ZoneId.systemDefault()
private val YEAR_MONTH_FMT = DateTimeFormatter.ofPattern("yyyy-MM")
private val DISPLAY_DATE_FMT = DateTimeFormatter.ofPattern("yyyy/MM/dd")
private val DISPLAY_DATETIME_FMT = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")

/** エポック日（days）→ LocalDate */
fun Long.toLocalDate(): LocalDate = LocalDate.ofEpochDay(this)

/** エポックミリ秒 → LocalDate */
fun Long.millisToLocalDate(): LocalDate = this.toLocalDateTime().toLocalDate()

fun LocalDateTime.toEpochMillis(): Long = this.atZone(ZONE).toInstant().toEpochMilli()
fun Long.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(
    java.time.Instant.ofEpochMilli(this), ZONE
)

fun YearMonth.toKey(): String = this.format(YEAR_MONTH_FMT)
fun String.toYearMonth(): YearMonth = YearMonth.parse(this, YEAR_MONTH_FMT)

fun LocalDate.displayFormat(): String = this.format(DISPLAY_DATE_FMT)
fun LocalDateTime.displayFormat(): String = this.format(DISPLAY_DATETIME_FMT)

/** クレカ締め日から支払月を計算（締め日を過ぎていれば翌々月、そうでなければ翌月） */
fun LocalDate.creditCardPaymentMonth(closingDay: Int): YearMonth {
    val closingDate = this.withDayOfMonth(minOf(closingDay, this.lengthOfMonth()))
    return if (this > closingDate) YearMonth.from(this).plusMonths(2)
    else YearMonth.from(this).plusMonths(1)
}
