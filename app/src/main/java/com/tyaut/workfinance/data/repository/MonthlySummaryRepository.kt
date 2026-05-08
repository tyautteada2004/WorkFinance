package com.tyaut.workfinance.data.repository

import com.tyaut.workfinance.data.db.dao.MonthlySummaryDao
import com.tyaut.workfinance.data.db.entity.MonthlySummaryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MonthlySummaryRepository {
    fun observeAll(): Flow<List<MonthlySummaryEntity>>
    fun observeRange(from: String, to: String): Flow<List<MonthlySummaryEntity>>
    suspend fun findByMonth(yearMonth: String): MonthlySummaryEntity?
    suspend fun upsert(summary: MonthlySummaryEntity): Long
    suspend fun markArchived(yearMonth: String, filePath: String)
}

class MonthlySummaryRepositoryImpl @Inject constructor(
    private val dao: MonthlySummaryDao
) : MonthlySummaryRepository {

    override fun observeAll() = dao.observeAll()
    override fun observeRange(from: String, to: String) = dao.observeRange(from, to)
    override suspend fun findByMonth(yearMonth: String) = dao.findByMonth(yearMonth)
    override suspend fun upsert(summary: MonthlySummaryEntity) = dao.upsert(summary)
    override suspend fun markArchived(yearMonth: String, filePath: String) =
        dao.markArchived(yearMonth, filePath)
}
