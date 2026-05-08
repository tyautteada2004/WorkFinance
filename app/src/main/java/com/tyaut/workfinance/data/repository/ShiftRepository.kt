package com.tyaut.workfinance.data.repository

import com.tyaut.workfinance.data.db.dao.ShiftDao
import com.tyaut.workfinance.data.db.entity.ShiftEntity
import com.tyaut.workfinance.domain.enums.ShiftStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ShiftRepository {
    fun observeByDateRange(fromMs: Long, toMs: Long): Flow<List<ShiftEntity>>
    fun observeByStatus(status: ShiftStatus): Flow<List<ShiftEntity>>
    suspend fun getUnconfirmed(): List<ShiftEntity>
    suspend fun findById(id: Long): ShiftEntity?
    suspend fun insert(shift: ShiftEntity): Long
    suspend fun update(shift: ShiftEntity)
    suspend fun updateActual(id: Long, actualStart: Long, actualEnd: Long, breakMinutes: Int, actualWage: Long)
    suspend fun confirm(id: Long, confirmedWage: Long)
    suspend fun updateCalendarEventId(id: Long, eventId: String?)
    suspend fun delete(shift: ShiftEntity)
}

class ShiftRepositoryImpl @Inject constructor(
    private val dao: ShiftDao
) : ShiftRepository {

    override fun observeByDateRange(fromMs: Long, toMs: Long) = dao.observeByDateRange(fromMs, toMs)
    override fun observeByStatus(status: ShiftStatus) = dao.observeByStatus(status)
    override suspend fun getUnconfirmed() = dao.getUnconfirmed()
    override suspend fun findById(id: Long) = dao.findById(id)
    override suspend fun insert(shift: ShiftEntity) = dao.insert(shift)
    override suspend fun update(shift: ShiftEntity) = dao.update(shift)
    override suspend fun updateActual(id: Long, actualStart: Long, actualEnd: Long, breakMinutes: Int, actualWage: Long) =
        dao.updateActual(id, actualStart, actualEnd, breakMinutes, actualWage)
    override suspend fun confirm(id: Long, confirmedWage: Long) = dao.confirm(id, confirmedWage)
    override suspend fun updateCalendarEventId(id: Long, eventId: String?) = dao.updateCalendarEventId(id, eventId)
    override suspend fun delete(shift: ShiftEntity) = dao.delete(shift)
}
