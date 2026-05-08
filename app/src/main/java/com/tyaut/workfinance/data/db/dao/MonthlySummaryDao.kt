package com.tyaut.workfinance.data.db.dao

import androidx.room.*
import com.tyaut.workfinance.data.db.entity.MonthlySummaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MonthlySummaryDao {

    @Query("SELECT * FROM monthly_summaries ORDER BY yearMonth DESC")
    fun observeAll(): Flow<List<MonthlySummaryEntity>>

    @Query("SELECT * FROM monthly_summaries WHERE yearMonth = :yearMonth")
    suspend fun findByMonth(yearMonth: String): MonthlySummaryEntity?

    @Query("SELECT * FROM monthly_summaries WHERE yearMonth BETWEEN :from AND :to ORDER BY yearMonth")
    fun observeRange(from: String, to: String): Flow<List<MonthlySummaryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(summary: MonthlySummaryEntity): Long

    @Query("""
        UPDATE monthly_summaries SET
            isArchived = 1,
            archiveFilePath = :filePath
        WHERE yearMonth = :yearMonth
    """)
    suspend fun markArchived(yearMonth: String, filePath: String)
}
