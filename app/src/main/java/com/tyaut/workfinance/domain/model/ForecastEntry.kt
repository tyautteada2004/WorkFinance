package com.tyaut.workfinance.domain.model

import java.time.LocalDate

/**
 * 予測タイムラインの1日分エントリ。
 * balance は前日繰越 + 当日の確定・予測収支の合計。
 */
data class ForecastEntry(
    val date: LocalDate,
    val balance: Long,
    val inflow: Long,
    val outflow: Long,
    val events: List<ForecastEvent>
) {
    val isDeficit: Boolean get() = balance < 0
}

data class ForecastEvent(
    val label: String,
    val amount: Long,
    val isConfirmed: Boolean
)
