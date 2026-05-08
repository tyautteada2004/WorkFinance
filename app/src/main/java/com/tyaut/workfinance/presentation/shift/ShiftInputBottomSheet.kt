package com.tyaut.workfinance.presentation.shift

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.tyaut.workfinance.data.db.entity.WorkplaceEntity
import com.tyaut.workfinance.databinding.BottomSheetShiftInputBinding
import com.tyaut.workfinance.domain.usecase.CalculateShiftWageUseCase
import com.tyaut.workfinance.util.CurrencyFormatter
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class ShiftInputBottomSheet : BottomSheetDialogFragment() {

    @Inject lateinit var calculateWage: CalculateShiftWageUseCase

    var workplaces: List<WorkplaceEntity> = emptyList()
    var onConfirm: ((workplaceId: Long, workplaceName: String, start: LocalDateTime, end: LocalDateTime, breakMinutes: Int) -> Unit)? = null

    private var _binding: BottomSheetShiftInputBinding? = null
    private val binding get() = _binding!!

    private var selectedDate: LocalDate = LocalDate.now()
    private var startTime: LocalTime = LocalTime.of(9, 0)
    private var endTime: LocalTime = LocalTime.of(17, 0)
    private var selectedWorkplace: WorkplaceEntity? = null

    private val dateFmt = DateTimeFormatter.ofPattern("yyyy/MM/dd (E)", Locale.JAPAN)
    private val timeFmt = DateTimeFormatter.ofPattern("HH:mm")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetShiftInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWorkplaceSpinner()
        setupDateField()
        setupTimeFields()
        updateDisplay()

        binding.btnConfirm.setOnClickListener { onConfirmClick() }
    }

    private fun setupWorkplaceSpinner() {
        val names = workplaces.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, names)
        binding.actWorkplace.setAdapter(adapter)
        binding.actWorkplace.setOnItemClickListener { _, _, position, _ ->
            selectedWorkplace = workplaces[position]
            updateWagePreview()
        }
        if (workplaces.isNotEmpty()) {
            selectedWorkplace = workplaces[0]
            binding.actWorkplace.setText(workplaces[0].name, false)
        }
    }

    private fun setupDateField() {
        binding.etDate.setOnClickListener { showDatePicker() }
    }

    private fun setupTimeFields() {
        binding.etStartTime.setOnClickListener { showTimePicker(isStart = true) }
        binding.etEndTime.setOnClickListener { showTimePicker(isStart = false) }
    }

    private fun showDatePicker() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("日付を選択")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
        picker.addOnPositiveButtonClickListener { millis ->
            selectedDate = Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
            updateDisplay()
        }
        picker.show(childFragmentManager, "date_picker")
    }

    private fun showTimePicker(isStart: Boolean) {
        val current = if (isStart) startTime else endTime
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(current.hour)
            .setMinute(current.minute)
            .setTitleText(if (isStart) "開始時刻" else "終了時刻")
            .build()
        picker.addOnPositiveButtonClickListener {
            val selected = LocalTime.of(picker.hour, picker.minute)
            if (isStart) startTime = selected else endTime = selected
            updateDisplay()
            updateWagePreview()
        }
        picker.show(childFragmentManager, if (isStart) "start_picker" else "end_picker")
    }

    private fun updateDisplay() {
        binding.etDate.setText(selectedDate.format(dateFmt))
        binding.etStartTime.setText(startTime.format(timeFmt))
        binding.etEndTime.setText(endTime.format(timeFmt))
        updateWagePreview()
    }

    private fun updateWagePreview() {
        val workplace = selectedWorkplace ?: return
        val start = LocalDateTime.of(selectedDate, startTime)
        val end = if (endTime > startTime) LocalDateTime.of(selectedDate, endTime)
                  else LocalDateTime.of(selectedDate.plusDays(1), endTime)
        val break_ = binding.etBreakMinutes.text.toString().toIntOrNull() ?: 0
        val wage = calculateWage(workplace, start, end, break_)
        binding.tvWagePreview.text = "予定賃金: ${CurrencyFormatter.format(wage)}"
    }

    private fun onConfirmClick() {
        val workplace = selectedWorkplace ?: return
        val break_ = binding.etBreakMinutes.text.toString().toIntOrNull() ?: 0
        val start = LocalDateTime.of(selectedDate, startTime)
        val end = if (endTime > startTime) LocalDateTime.of(selectedDate, endTime)
                  else LocalDateTime.of(selectedDate.plusDays(1), endTime)
        onConfirm?.invoke(workplace.id, workplace.name, start, end, break_)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
