package com.tyaut.workfinance.presentation.shift

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyaut.workfinance.data.db.entity.ShiftEntity
import com.tyaut.workfinance.data.db.entity.WorkplaceEntity
import com.tyaut.workfinance.data.repository.ShiftRepository
import com.tyaut.workfinance.data.repository.WorkplaceRepository
import com.tyaut.workfinance.domain.enums.ShiftStatus
import com.tyaut.workfinance.domain.usecase.CalculateShiftWageUseCase
import com.tyaut.workfinance.domain.usecase.ConfirmShiftUseCase
import com.tyaut.workfinance.util.GoogleCalendarManager
import com.tyaut.workfinance.util.ReminderScheduler
import com.tyaut.workfinance.util.toLocalDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ShiftViewModel @Inject constructor(
    private val shiftRepo: ShiftRepository,
    private val workplaceRepo: WorkplaceRepository,
    private val calculateWage: CalculateShiftWageUseCase,
    private val confirmShift: ConfirmShiftUseCase,
    private val reminder: ReminderScheduler,
    private val calendar: GoogleCalendarManager,
) : ViewModel() {

    private val _shifts = MutableStateFlow<List<ShiftEntity>>(emptyList())
    val shifts: StateFlow<List<ShiftEntity>> = _shifts

    private val _workplaces = MutableStateFlow<List<WorkplaceEntity>>(emptyList())
    val workplaces: StateFlow<List<WorkplaceEntity>> = _workplaces

    init {
        viewModelScope.launch {
            workplaceRepo.observeAll().collect { _workplaces.value = it }
        }
        loadMonth(LocalDate.now())
    }

    fun loadMonth(month: LocalDate) {
        viewModelScope.launch {
            val from = month.withDayOfMonth(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val to = month.withDayOfMonth(month.lengthOfMonth()).atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            shiftRepo.observeByDateRange(from, to).collect { _shifts.value = it }
        }
    }

    fun addShift(workplaceId: Long, workplaceName: String, start: LocalDateTime, end: LocalDateTime, breakMinutes: Int = 0) {
        viewModelScope.launch {
            val workplace = workplaceRepo.findById(workplaceId) ?: return@launch
            val wage = calculateWage(workplace, start, end, breakMinutes)
            val zone = ZoneId.systemDefault()
            val shiftId = shiftRepo.insert(
                ShiftEntity(
                    workplaceId = workplaceId,
                    workplaceName = workplaceName,
                    scheduledStart = start.atZone(zone).toInstant().toEpochMilli(),
                    scheduledEnd = end.atZone(zone).toInstant().toEpochMilli(),
                    scheduledWage = wage
                )
            )
            val inserted = shiftRepo.findById(shiftId) ?: return@launch
            reminder.scheduleShiftReminder(inserted, workplace.preWorkNotifyMinutes)
            val eventId = calendar.addShiftEvent(inserted)
            if (eventId != null) shiftRepo.updateCalendarEventId(shiftId, eventId)
        }
    }

    fun updateActual(shiftId: Long, actualStart: LocalDateTime, actualEnd: LocalDateTime, breakMinutes: Int) {
        viewModelScope.launch {
            val shift = shiftRepo.findById(shiftId) ?: return@launch
            val workplace = workplaceRepo.findById(shift.workplaceId) ?: return@launch
            val wage = calculateWage(workplace, actualStart, actualEnd, breakMinutes)
            val zone = ZoneId.systemDefault()
            shiftRepo.updateActual(
                shiftId,
                actualStart.atZone(zone).toInstant().toEpochMilli(),
                actualEnd.atZone(zone).toInstant().toEpochMilli(),
                breakMinutes,
                wage
            )
        }
    }

    fun confirm(shiftId: Long, confirmedWage: Long) {
        viewModelScope.launch { confirmShift(shiftId, confirmedWage) }
    }

    fun deleteShift(shift: ShiftEntity) {
        viewModelScope.launch {
            reminder.cancelShiftReminder(shift.id)
            shift.googleCalendarEventId?.let { calendar.deleteEvent(it) }
            shiftRepo.delete(shift)
        }
    }
}
