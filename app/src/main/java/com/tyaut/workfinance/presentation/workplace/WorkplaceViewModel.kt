package com.tyaut.workfinance.presentation.workplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyaut.workfinance.data.db.entity.WorkplaceEntity
import com.tyaut.workfinance.data.repository.AccountRepository
import com.tyaut.workfinance.data.repository.WorkplaceRepository
import com.tyaut.workfinance.domain.enums.AccountType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkplaceViewModel @Inject constructor(
    private val workplaceRepo: WorkplaceRepository,
    private val accountRepo: AccountRepository,
) : ViewModel() {

    val workplaces: StateFlow<List<WorkplaceEntity>> = workplaceRepo.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bankAccounts: StateFlow<List<com.tyaut.workfinance.data.db.entity.AccountEntity>> =
        accountRepo.observeByType(AccountType.BANK)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun save(workplace: WorkplaceEntity) {
        viewModelScope.launch {
            if (workplace.id == 0L) workplaceRepo.insert(workplace)
            else workplaceRepo.update(workplace)
        }
    }

    fun deactivate(id: Long) {
        viewModelScope.launch { workplaceRepo.deactivate(id) }
    }
}
