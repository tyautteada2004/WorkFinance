package com.tyaut.workfinance.data.repository

import com.tyaut.workfinance.data.db.dao.TransactionDao
import com.tyaut.workfinance.data.db.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TransactionRepository {
    fun observeByDateRange(fromEpochDay: Long, toEpochDay: Long): Flow<List<TransactionEntity>>
    fun observeByAccount(accountId: Long): Flow<List<TransactionEntity>>
    fun observeUnconfirmedByPaymentMonth(yearMonth: String): Flow<List<TransactionEntity>>
    suspend fun getFutureCardTransactions(fromYearMonth: String): List<TransactionEntity>
    suspend fun findById(id: Long): TransactionEntity?
    suspend fun insert(transaction: TransactionEntity): Long
    suspend fun insertAll(transactions: List<TransactionEntity>)
    suspend fun update(transaction: TransactionEntity)
    suspend fun confirmCreditCard(id: Long, paymentMonth: String)
    suspend fun sumAmountUpTo(accountId: Long, upToEpochDay: Long): Long
    suspend fun deleteByDateRange(fromEpochDay: Long, toEpochDay: Long)
}

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override fun observeByDateRange(fromEpochDay: Long, toEpochDay: Long) =
        dao.observeByDateRange(fromEpochDay, toEpochDay)

    override fun observeByAccount(accountId: Long) = dao.observeByAccount(accountId)

    override fun observeUnconfirmedByPaymentMonth(yearMonth: String) =
        dao.observeUnconfirmedByPaymentMonth(yearMonth)

    override suspend fun getFutureCardTransactions(fromYearMonth: String) =
        dao.getFutureCardTransactions(fromYearMonth)

    override suspend fun findById(id: Long) = dao.findById(id)
    override suspend fun insert(transaction: TransactionEntity) = dao.insert(transaction)
    override suspend fun insertAll(transactions: List<TransactionEntity>) = dao.insertAll(transactions)
    override suspend fun update(transaction: TransactionEntity) = dao.update(transaction)

    override suspend fun confirmCreditCard(id: Long, paymentMonth: String) =
        dao.confirmCreditCardTransaction(id, paymentMonth)

    override suspend fun sumAmountUpTo(accountId: Long, upToEpochDay: Long) =
        dao.sumAmountUpTo(accountId, upToEpochDay) ?: 0L

    override suspend fun deleteByDateRange(fromEpochDay: Long, toEpochDay: Long) =
        dao.deleteByDateRange(fromEpochDay, toEpochDay)
}
