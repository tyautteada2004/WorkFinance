package com.tyaut.workfinance.data.repository

import com.tyaut.workfinance.data.db.dao.FixedExpenseDao
import com.tyaut.workfinance.data.db.entity.FixedExpenseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FixedExpenseRepository {
    fun observeAll(): Flow<List<FixedExpenseEntity>>
    suspend fun findById(id: Long): FixedExpenseEntity?
    suspend fun getActiveOnDate(targetEpochDay: Long): List<FixedExpenseEntity>
    suspend fun insert(fixedExpense: FixedExpenseEntity): Long
    suspend fun update(fixedExpense: FixedExpenseEntity)
    suspend fun deactivate(id: Long)
}

class FixedExpenseRepositoryImpl @Inject constructor(
    private val dao: FixedExpenseDao
) : FixedExpenseRepository {

    override fun observeAll() = dao.observeAll()
    override suspend fun findById(id: Long) = dao.findById(id)
    override suspend fun getActiveOnDate(targetEpochDay: Long) = dao.getActiveOnDate(targetEpochDay)
    override suspend fun insert(fixedExpense: FixedExpenseEntity) = dao.insert(fixedExpense)
    override suspend fun update(fixedExpense: FixedExpenseEntity) = dao.update(fixedExpense)
    override suspend fun deactivate(id: Long) = dao.deactivate(id)
}
