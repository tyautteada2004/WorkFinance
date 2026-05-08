package com.tyaut.workfinance.data.db.dao

import androidx.room.*
import com.tyaut.workfinance.data.db.entity.WorkplaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkplaceDao {

    @Query("SELECT * FROM workplaces WHERE isActive = 1 ORDER BY name")
    fun observeAll(): Flow<List<WorkplaceEntity>>

    @Query("SELECT * FROM workplaces WHERE id = :id")
    suspend fun findById(id: Long): WorkplaceEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(workplace: WorkplaceEntity): Long

    @Update
    suspend fun update(workplace: WorkplaceEntity)

    @Query("UPDATE workplaces SET isActive = 0 WHERE id = :id")
    suspend fun deactivate(id: Long)
}
