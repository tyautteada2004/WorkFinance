package com.tyaut.workfinance.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyaut.workfinance.domain.model.ForecastEntry
import com.tyaut.workfinance.domain.model.NetWorthSnapshot
import com.tyaut.workfinance.domain.usecase.CalculateNetWorthUseCase
import com.tyaut.workfinance.domain.usecase.GetForecastTimelineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getForecastTimeline: GetForecastTimelineUseCase,
    private val calculateNetWorth: CalculateNetWorthUseCase,
) : ViewModel() {

    private val _forecast = MutableStateFlow<List<ForecastEntry>>(emptyList())
    val forecast: StateFlow<List<ForecastEntry>> = _forecast

    private val _netWorth = MutableStateFlow<NetWorthSnapshot?>(null)
    val netWorth: StateFlow<NetWorthSnapshot?> = _netWorth

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _forecast.value = getForecastTimeline(LocalDate.now(), daysAhead = 60)
                _netWorth.value = calculateNetWorth()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
