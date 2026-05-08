package com.tyaut.workfinance.data.db.dao

import androidx.room.*
import com.tyaut.workfinance.data.db.entity.AccountEntity
import com.tyaut.workfinance.domain.enums.AccountType
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Query("SELECT * FROM accounts WHERE isActive = 1 ORDER BY type, name")
    fun observeAll(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun findById(id: Long): AccountEntity?

    @Query("SELECT * FROM accounts WHERE type = :type AND isActive = 1")
    fun observeByType(type: AccountType): Flow<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(account: AccountEntity): Long

    /** 初期残高(initialBalance)は更新不可 — 名前・設定のみ更新する */
    @Query("""
        UPDATE accounts SET
            name = :name,
            closingDay = :closingDay,
            paymentDay = :paymentDay,
            businessDayRule = :businessDayRule,
            isActive = :isActive
        WHERE id = :id
    """)
    suspend fun updateSettings(
        id: Long,
        name: String,
        closingDay: Int?,
        paymentDay: Int?,
        businessDayRule: String?,
        isActive: Boolean
    )

    @Query("UPDATE accounts SET isActive = 0 WHERE id = :id")
    suspend fun deactivate(id: Long)
}
