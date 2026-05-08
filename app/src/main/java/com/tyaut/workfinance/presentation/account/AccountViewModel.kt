package com.tyaut.workfinance.presentation.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyaut.workfinance.data.db.entity.AccountEntity
import com.tyaut.workfinance.data.repository.AccountRepository
import com.tyaut.workfinance.data.repository.TransactionRepository
import com.tyaut.workfinance.domain.enums.AccountType
import com.tyaut.workfinance.domain.enums.BusinessDayRule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepo: AccountRepository,
    private val transactionRepo: TransactionRepository,
) : ViewModel() {

    /** 各口座の「現在残高（初期残高 + 累計取引額）」を付与したリスト */
    val accountsWithBalance: StateFlow<List<AccountWithBalance>> = accountRepo.observeAll()
        .map { accounts ->
            val today = LocalDate.now().toEpochDay()
            accounts.map { account ->
                val delta = transactionRepo.sumAmountUpTo(account.id, today)
                AccountWithBalance(account, account.initialBalance + delta)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** 新規アカウント作成: 初期残高はここで固定され、以後変更不可 */
    fun createAccount(
        name: String,
        type: AccountType,
        initialBalance: Long,
        closingDay: Int? = null,
        paymentDay: Int? = null,
        businessDayRule: BusinessDayRule? = null
    ) {
        viewModelScope.launch {
            accountRepo.create(
                AccountEntity(
                    name = name,
                    type = type,
                    initialBalance = initialBalance,
                    closingDay = closingDay,
                    paymentDay = paymentDay,
                    businessDayRule = businessDayRule
                )
            )
        }
    }

    fun updateSettings(
        id: Long, name: String, closingDay: Int?, paymentDay: Int?,
        businessDayRule: BusinessDayRule?, isActive: Boolean
    ) {
        viewModelScope.launch {
            accountRepo.updateSettings(
                id, name, closingDay, paymentDay,
                businessDayRule?.name, isActive
            )
        }
    }
}
