package com.tyaut.workfinance.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tyaut.workfinance.domain.enums.BusinessDayRule
import com.tyaut.workfinance.domain.enums.ExpenseCategory

/**
 * 固定収支予約（サブスク等）。ライフサイクル（開始〜終了）で管理し、
 * 期間外は予測タイムラインから自動除外する。
 * accountName を非正規化し、アーカイブ後も参照可能にする。
 */
@Entity(
    tableName = "fixed_expenses",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("accountId")]
)
data class FixedExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    /** 負値を許容（定期入金にも使用可） */
    val amount: Long,
    val category: ExpenseCategory,
    val accountId: Long,
    /** 非正規化: アーカイブ後も参照可能 */
    val accountName: String,
    /** エポック日（必須） */
    val startDate: Long,
    /** エポック日（任意: 終了日なしは無期限） */
    val endDate: Long? = null,
    /** 毎月何日に生成するか（1–28） */
    val dayOfMonth: Int,
    val businessDayRule: BusinessDayRule? = null,
    val isActive: Boolean = true
)
