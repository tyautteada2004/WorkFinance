package com.tyaut.workfinance.domain.model

/**
 * 純資産スナップショット。
 * netWorth = totalAssets - totalLiabilities
 */
data class NetWorthSnapshot(
    val totalAssets: Long,
    val totalLiabilities: Long
) {
    val netWorth: Long get() = totalAssets - totalLiabilities
}
