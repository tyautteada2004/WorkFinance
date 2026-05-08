package com.tyaut.workfinance.presentation.shift

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tyaut.workfinance.data.db.entity.ShiftEntity
import com.tyaut.workfinance.databinding.FragmentShiftBinding
import com.tyaut.workfinance.domain.enums.ShiftStatus
import com.tyaut.workfinance.util.CurrencyFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShiftFragment : Fragment() {

    private var _binding: FragmentShiftBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShiftViewModel by viewModels()

    private val shiftAdapter = ShiftAdapter(onShiftClick = ::onShiftClick)

    /** カレンダー権限リクエスト。結果に関わらずダイアログを表示（同期はベストエフォート） */
    private val requestCalendarPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { showAddShiftDialog() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShiftBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvShifts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = shiftAdapter
        }

        binding.fabAddShift.setOnClickListener { onFabAddClick() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.shifts.collect { shifts ->
                    shiftAdapter.submitList(shifts)
                }
            }
        }
    }

    private fun onShiftClick(shift: ShiftEntity) {
        val actions = when (shift.status) {
            ShiftStatus.SCHEDULED -> arrayOf("実績を入力する", "削除する")
            ShiftStatus.ACTUAL    -> arrayOf("確定する（振込確認済み）", "削除する")
            ShiftStatus.CONFIRMED -> arrayOf("詳細を見る")
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(shift.workplaceName)
            .setItems(actions) { _, which ->
                when (shift.status) {
                    ShiftStatus.SCHEDULED -> if (which == 0) showActualInputDialog(shift)
                                            else confirmDelete(shift)
                    ShiftStatus.ACTUAL    -> if (which == 0) showConfirmWageDialog(shift)
                                            else confirmDelete(shift)
                    ShiftStatus.CONFIRMED -> { /* 詳細表示（TODO） */ }
                }
            }
            .show()
    }

    private fun showActualInputDialog(shift: ShiftEntity) {
        ActualShiftInputBottomSheet().apply {
            this.shift = shift
            workplace = viewModel.workplaces.value.find { it.id == shift.workplaceId }
            onConfirm = { actualStart, actualEnd, breakMinutes ->
                viewModel.updateActual(shift.id, actualStart, actualEnd, breakMinutes)
            }
        }.show(childFragmentManager, "actual_shift")
    }

    private fun showConfirmWageDialog(shift: ShiftEntity) {
        val wage = shift.actualWage ?: return
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("振込確認")
            .setMessage("計算賃金: ${CurrencyFormatter.format(wage)}\nこの金額で確定しますか？")
            .setPositiveButton("確定") { _, _ -> viewModel.confirm(shift.id, wage) }
            .setNegativeButton("金額を修正") { _, _ ->
                // TODO: 金額入力ダイアログ（端数・税調整用）
            }
            .show()
    }

    private fun confirmDelete(shift: ShiftEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("削除確認")
            .setMessage("このシフトを削除しますか？")
            .setPositiveButton("削除") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.deleteShift(shift)
                }
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }

    private fun onFabAddClick() {
        if (hasCalendarPermission()) showAddShiftDialog()
        else requestCalendarPermission.launch(
            arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)
        )
    }

    private fun hasCalendarPermission(): Boolean =
        ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_CALENDAR) ==
            PackageManager.PERMISSION_GRANTED

    private fun showAddShiftDialog() {
        ShiftInputBottomSheet().apply {
            workplaces = viewModel.workplaces.value
            onConfirm = { workplaceId, workplaceName, start, end, breakMinutes ->
                viewModel.addShift(workplaceId, workplaceName, start, end, breakMinutes)
            }
        }.show(childFragmentManager, "add_shift")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
