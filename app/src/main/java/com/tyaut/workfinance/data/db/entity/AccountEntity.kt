package com.tyaut.workfinance.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tyaut.workfinance.domain.enums.AccountType
import com.tyaut.workfinance.domain.enums.BusinessDayRule

@Entity(tableName = "accounts")
data class AccountEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: AccountType,
    /** 作成時に登録し、以後UIからの変更を禁止（ロック） */
    val initialBalance: Long,
    /** クレカ用: 締め日（1–31） */
    val closingDay: Int? = null,
    /** クレカ用: 支払日（1–31） */
    val paymentDay: Int? = null,
    /** 給料日/引落日の営業日補正ルール */
    val businessDayRule: BusinessDayRule? = null,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
