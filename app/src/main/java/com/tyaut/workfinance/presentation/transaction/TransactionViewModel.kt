package com.tyaut.workfinance.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyaut.workfinance.data.db.entity.AccountEntity
import com.tyaut.workfinance.data.db.entity.TransactionEntity
import com.tyaut.workfinance.data.repository.AccountRepository
import com.tyaut.workfinance.data.repository.TransactionRepository
import com.tyaut.workfinance.domain.enums.ExpenseCategory
import com.tyaut.workfinance.domain.enums.TransactionType
import com.tyaut.workfinance.domain.usecase.ConfirmCreditCardUseCase
import com.tyaut.workfinance.util.creditCardPaymentMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepo: TransactionRepository,
    private val accountRepo: AccountRepository,
    private val confirmCreditCard: ConfirmCreditCardUseCase,
) : ViewModel() {

    private val _accounts = MutableStateFlow<List<AccountEntity>>(emptyList())
    val accounts: StateFlow<List<AccountEntity>> = _accounts

    init {
        viewModelScope.launch {
            accountRepo.observeAll().collect { _accounts.value = it }
        }
    }

    /**
     * クイック支出入力。amount は正値で渡す（内部でマイナス変換）。
     * マイナス支出（返金・ポイント充当）は amount に負値を渡す。
     */
    fun addExpense(
        accountId: Long,
        amount: Long,
        category: ExpenseCategory,
        description: String,
        date: LocalDate = LocalDate.now()
    ) {
        viewModelScope.launch {
            val account = accountRepo.findById(accountId) ?: return@launch
            val paymentMonth = if (account.closingDay != null) {
                date.creditCardPaymentMonth(account.closingDay).toString()
            } else null

            transactionRepo.insert(
                TransactionEntity(
                    accountId = accountId,
                    amount = -amount,  // 支出はマイナス
                    type = TransactionType.EXPENSE,
                    category = category,
                    description = description,
                    transactionDate = date.toEpochDay(),
                    isConfirmed = account.closingDay == null,
                    creditCardPaymentMonth = paymentMonth
                )
            )
        }
    }

    fun addIncome(accountId: Long, amount: Long, description: String, date: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            transactionRepo.insert(
                TransactionEntity(
                    accountId = accountId,
                    amount = amount,
                    type = TransactionType.INCOME,
                    description = description,
                    transactionDate = date.toEpochDay(),
                    isConfirmed = true
                )
            )
        }
    }

    /**
     * 振替処理（資産総額を変えずに口座間移動）。
     * 借入時（奨学金等）は fromAccountId に負債口座を指定し、
     * toAccountId に現金/銀行を指定する。
     */
    fun addTransfer(fromAccountId: Long, toAccountId: Long, amount: Long, description: String, date: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            val fromTx = TransactionEntity(
                accountId = fromAccountId,
                amount = -amount,
                type = TransactionType.TRANSFER,
                description = description,
                transactionDate = date.toEpochDay(),
                isConfirmed = true
            )
            val fromId = transactionRepo.insert(fromTx)

            val toTx = TransactionEntity(
                accountId = toAccountId,
                amount = amount,
                type = TransactionType.TRANSFER,
                description = description,
                transactionDate = date.toEpochDay(),
                isConfirmed = true,
                pairedTransactionId = fromId
            )
            val toId = transactionRepo.insert(toTx)

            // 双方に相手IDを設定
            transactionRepo.findById(fromId)?.let { tx ->
                transactionRepo.update(tx.copy(pairedTransactionId = toId))
            }
        }
    }

    fun getPaymentMonthOptions(calculatedMonth: YearMonth) =
        confirmCreditCard.paymentMonthOptions(calculatedMonth)

    fun confirmCard(transactionId: Long, paymentMonth: YearMonth) {
        viewModelScope.launch { confirmCreditCard(transactionId, paymentMonth) }
    }
}

