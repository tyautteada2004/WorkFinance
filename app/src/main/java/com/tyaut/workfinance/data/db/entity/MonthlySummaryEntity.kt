package com.tyaut.workfinance.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 月次サマリー。アーカイブ後も統計グラフ描画に使えるよう、
 * 合計値のみをメインDBに残す。
 */
@Entity(tableName = "monthly_summaries")
data class MonthlySummaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    /** "YYYY-MM" */
    val yearMonth: String,
    val totalIncome: Long = 0,
    /** 合計支出（マイナス支出込み） */
    val totalExpense: Long = 0,
    val totalWorkMinutes: Long = 0,
    val totalConsumption: Long = 0,
    val totalInvestment: Long = 0,
    val totalWaste: Long = 0,
    /** true: 詳細データはアーカイブファイルに移動済み */
    val isArchived: Boolean = false,
    /** アーカイブSQLiteファイルの絶対パス */
    val archiveFilePath: String? = null
)
