package com.tyaut.workfinance.data.repository

import com.tyaut.workfinance.data.db.dao.AccountDao
import com.tyaut.workfinance.data.db.entity.AccountEntity
import com.tyaut.workfinance.domain.enums.AccountType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AccountRepository {
    fun observeAll(): Flow<List<AccountEntity>>
    fun observeByType(type: AccountType): Flow<List<AccountEntity>>
    suspend fun findById(id: Long): AccountEntity?
    suspend fun create(account: AccountEntity): Long
    suspend fun updateSettings(
        id: Long, name: String, closingDay: Int?, paymentDay: Int?,
        businessDayRule: String?, isActive: Boolean
    )
    suspend fun deactivate(id: Long)
}

class AccountRepositoryImpl @Inject constructor(
    private val dao: AccountDao
) : AccountRepository {

    override fun observeAll() = dao.observeAll()
    override fun observeByType(type: AccountType) = dao.observeByType(type)
    override suspend fun findById(id: Long) = dao.findById(id)
    override suspend fun create(account: AccountEntity) = dao.insert(account)
    override suspend fun updateSettings(
        id: Long, name: String, closingDay: Int?, paymentDay: Int?,
        businessDayRule: String?, isActive: Boolean
    ) = dao.updateSettings(id, name, closingDay, paymentDay, businessDayRule, isActive)
    override suspend fun deactivate(id: Long) = dao.deactivate(id)
}
