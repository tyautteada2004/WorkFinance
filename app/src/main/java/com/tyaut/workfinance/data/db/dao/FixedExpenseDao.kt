package com.tyaut.workfinance.data.db.dao

import androidx.room.*
import com.tyaut.workfinance.data.db.entity.FixedExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FixedExpenseDao {

    @Query("SELECT * FROM fixed_expenses WHERE isActive = 1 ORDER BY name")
    fun observeAll(): Flow<List<FixedExpenseEntity>>

    @Query("SELECT * FROM fixed_expenses WHERE id = :id")
    suspend fun findById(id: Long): FixedExpenseEntity?

    /**
     * 指定日時点で有効な固定収支を取得。
     * startDate <= targetDate かつ (endDate IS NULL OR endDate >= targetDate)
     */
    @Query("""
        SELECT * FROM fixed_expenses
        WHERE isActive = 1
          AND startDate <= :targetEpochDay
          AND (endDate IS NULL OR endDate >= :targetEpochDay)
    """)
    suspend fun getActiveOnDate(targetEpochDay: Long): List<FixedExpenseEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(fixedExpense: FixedExpenseEntity): Long

    @Update
    suspend fun update(fixedExpense: FixedExpenseEntity)

    @Query("UPDATE fixed_expenses SET isActive = 0 WHERE id = :id")
    suspend fun deactivate(id: Long)
}
