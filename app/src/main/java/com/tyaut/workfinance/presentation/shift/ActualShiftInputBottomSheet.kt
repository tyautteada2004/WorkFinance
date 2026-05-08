package com.tyaut.workfinance.presentation.shift

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.tyaut.workfinance.data.db.entity.ShiftEntity
import com.tyaut.workfinance.data.db.entity.WorkplaceEntity
import com.tyaut.workfinance.databinding.BottomSheetActualShiftBinding
import com.tyaut.workfinance.domain.usecase.CalculateShiftWageUseCase
import com.tyaut.workfinance.util.CurrencyFormatter
import com.tyaut.workfinance.util.toLocalDateTime
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class ActualShiftInputBottomSheet : BottomSheetDialogFragment() {

    @Inject lateinit var calculateWage: CalculateShiftWageUseCase

    var shift: ShiftEntity? = null
    var workplace: WorkplaceEntity? = null
    var onConfirm: ((actualStart: LocalDateTime, actualEnd: LocalDateTime, breakMinutes: Int) -> Unit)? = null

    private var _binding: BottomSheetActualShiftBinding? = null
    private val binding get() = _binding!!

    private var actualStart: LocalDateTime = LocalDateTime.now()
    private var actualEnd: LocalDateTime = LocalDateTime.now().plusHours(8)

    private val timeFmt = DateTimeFormatter.ofPattern("HH:mm")
    private val dateTimeFmt = DateTimeFormatter.ofPattern("M/d(E) HH:mm", java.util.Locale.JAPAN)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetActualShiftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shift?.let { s ->
            // 予定時刻をデフォルトとして設定
            actualStart = s.scheduledStart.toLocalDateTime()
            actualEnd = s.scheduledEnd.toLocalDateTime()
            binding.tvScheduledInfo.text =
                "予定: ${actualStart.format(dateTimeFmt)} 〜 ${actualEnd.format(timeFmt)}"
        }

        updateDisplay()

        binding.etActualStart.setOnClickListener { showTimePicker(isStart = true) }
        binding.etActualEnd.setOnClickListener { showTimePicker(isStart = false) }
        binding.btnConfirm.setOnClickListener { onConfirmClick() }
    }

    private fun showTimePicker(isStart: Boolean) {
        val current = if (isStart) actualStart.toLocalTime() else actualEnd.toLocalTime()
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(current.hour)
            .setMinute(current.minute)
            .setTitleText(if (isStart) "実際の開始時刻" else "実際の終了時刻")
            .build()
        picker.addOnPositiveButtonClickListener {
            val date = if (isStart) actualStart.toLocalDate() else actualEnd.toLocalDate()
            val newTime = LocalTime.of(picker.hour, picker.minute)
            if (isStart) actualStart = LocalDateTime.of(date, newTime)
            else actualEnd = LocalDateTime.of(date, newTime)
            updateDisplay()
        }
        picker.show(childFragmentManager, if (isStart) "actual_start" else "actual_end")
    }

    private fun updateDisplay() {
        binding.etActualStart.setText(actualStart.format(timeFmt))
        binding.etActualEnd.setText(actualEnd.format(timeFmt))
        updateWagePreview()
    }

    private fun updateWagePreview() {
        val wp = workplace ?: return
        val break_ = binding.etBreakMinutes.text.toString().toIntOrNull() ?: 0
        val wage = calculateWage(wp, actualStart, actualEnd, break_)
        binding.tvWagePreview.text = "計算賃金: ${CurrencyFormatter.format(wage)}"
    }

    private fun onConfirmClick() {
        val break_ = binding.etBreakMinutes.text.toString().toIntOrNull() ?: 0
        onConfirm?.invoke(actualStart, actualEnd, break_)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
