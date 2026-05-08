package com.tyaut.workfinance.data.db.dao

import androidx.room.*
import com.tyaut.workfinance.data.db.entity.ShiftEntity
import com.tyaut.workfinance.domain.enums.ShiftStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftDao {

    @Query("SELECT * FROM shifts WHERE scheduledStart BETWEEN :fromMs AND :toMs ORDER BY scheduledStart")
    fun observeByDateRange(fromMs: Long, toMs: Long): Flow<List<ShiftEntity>>

    @Query("SELECT * FROM shifts WHERE id = :id")
    suspend fun findById(id: Long): ShiftEntity?

    @Query("SELECT * FROM shifts WHERE status = :status ORDER BY scheduledStart")
    fun observeByStatus(status: ShiftStatus): Flow<List<ShiftEntity>>

    @Query("SELECT * FROM shifts WHERE status IN ('SCHEDULED', 'ACTUAL') ORDER BY scheduledStart")
    suspend fun getUnconfirmed(): List<ShiftEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(shift: ShiftEntity): Long

    @Update
    suspend fun update(shift: ShiftEntity)

    /** 実績入力: 実働時間と計算賃金を更新、ステータスを ACTUAL に */
    @Query("""
        UPDATE shifts SET
            status = 'ACTUAL',
            actualStart = :actualStart,
            actualEnd = :actualEnd,
            breakMinutes = :breakMinutes,
            actualWage = :actualWage
        WHERE id = :id
    """)
    suspend fun updateActual(id: Long, actualStart: Long, actualEnd: Long, breakMinutes: Int, actualWage: Long)

    /** 確定: 所得税等の最終調整額を記録 */
    @Query("""
        UPDATE shifts SET
            status = 'CONFIRMED',
            confirmedWage = :confirmedWage
        WHERE id = :id
    """)
    suspend fun confirm(id: Long, confirmedWage: Long)

    @Query("UPDATE shifts SET googleCalendarEventId = :eventId WHERE id = :id")
    suspend fun updateCalendarEventId(id: Long, eventId: String?)

    @Delete
    suspend fun delete(shift: ShiftEntity)

    @Query("DELETE FROM shifts WHERE scheduledStart BETWEEN :fromMs AND :toMs")
    suspend fun deleteByDateRange(fromMs: Long, toMs: Long)
}
