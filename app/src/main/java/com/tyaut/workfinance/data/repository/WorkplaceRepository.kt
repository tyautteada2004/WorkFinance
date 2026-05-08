package com.tyaut.workfinance.data.repository

import com.tyaut.workfinance.data.db.dao.WorkplaceDao
import com.tyaut.workfinance.data.db.entity.WorkplaceEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WorkplaceRepository {
    fun observeAll(): Flow<List<WorkplaceEntity>>
    suspend fun findById(id: Long): WorkplaceEntity?
    suspend fun insert(workplace: WorkplaceEntity): Long
    suspend fun update(workplace: WorkplaceEntity)
    suspend fun deactivate(id: Long)
}

class WorkplaceRepositoryImpl @Inject constructor(
    private val dao: WorkplaceDao
) : WorkplaceRepository {

    override fun observeAll() = dao.observeAll()
    override suspend fun findById(id: Long) = dao.findById(id)
    override suspend fun insert(workplace: WorkplaceEntity) = dao.insert(workplace)
    override suspend fun update(workplace: WorkplaceEntity) = dao.update(workplace)
    override suspend fun deactivate(id: Long) = dao.deactivate(id)
}
