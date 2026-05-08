package com.tyaut.workfinance.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyaut.workfinance.data.db.entity.MonthlySummaryEntity
import com.tyaut.workfinance.data.db.entity.TransactionEntity
import com.tyaut.workfinance.data.repository.TransactionRepository
import com.tyaut.workfinance.domain.enums.ExpenseCategory
import com.tyaut.workfinance.domain.enums.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

data class CategoryStats(
    val consumption: Long,
    val investment: Long,
    val waste: Long,
    val totalExpense: Long,
    val totalIncome: Long
)

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val transactionRepo: TransactionRepository,
) : ViewModel() {

    private val _stats = MutableStateFlow<CategoryStats?>(null)
    val stats: StateFlow<CategoryStats?> = _stats

    fun loadMonth(yearMonth: YearMonth = YearMonth.now()) {
        viewModelScope.launch {
            val from = yearMonth.atDay(1).toEpochDay()
            val to = yearMonth.atEndOfMonth().toEpochDay()

            transactionRepo.observeByDateRange(from, to).collect { transactions ->
                // 振替・残高調整は三分類から除外
                val eligible = transactions.filter {
                    it.type == TransactionType.EXPENSE || it.type == TransactionType.INCOME
                }

                val consumption = eligible.filter { it.category == ExpenseCategory.CONSUMPTION }.sumOf { it.amount }
                val investment = eligible.filter { it.category == ExpenseCategory.INVESTMENT }.sumOf { it.amount }
                val waste = eligible.filter { it.category == ExpenseCategory.WASTE }.sumOf { it.amount }
                val income = eligible.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
                val expense = eligible.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }

                _stats.value = CategoryStats(
                    consumption = -consumption,  // 正値で返す
                    investment = -investment,
                    waste = -waste,
                    totalExpense = -expense,
                    totalIncome = income
                )
            }
        }
    }
}
