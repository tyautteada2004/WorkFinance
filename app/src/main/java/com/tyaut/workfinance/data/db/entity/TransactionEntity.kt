package com.tyaut.workfinance.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tyaut.workfinance.domain.enums.ExpenseCategory
import com.tyaut.workfinance.domain.enums.TransactionType

/**
 * 全収支レコード。amount は負値を許容（赤黒処理）。
 * クレカ返金・ポイント充当はマイナス支出として記録し、データを削除・上書きしない。
 */
@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("accountId"), Index("transactionDate"), Index("creditCardPaymentMonth")]
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val accountId: Long,
    /** 負値を許容。支出はマイナス、収入はプラスで統一 */
    val amount: Long,
    val type: TransactionType,
    /** TRANSFER / BALANCE_ADJUST のときは null */
    val category: ExpenseCategory? = null,
    val description: String,
    /** エポック日（LocalDate.toEpochDay()） */
    val transactionDate: Long,
    /** クレカ利用時: 請求確定前は false */
    val isConfirmed: Boolean = false,
    /** クレカ請求月 "YYYY-MM"。確定操作で前後1ヶ月から手動選択可 */
    val creditCardPaymentMonth: String? = null,
    /** 振替の相手側レコードID */
    val pairedTransactionId: Long? = null,
    /** 固定収支予約から自動生成された場合 true */
    val isFromFixedExpense: Boolean = false,
    val fixedExpenseId: Long? = null,
    val note: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
