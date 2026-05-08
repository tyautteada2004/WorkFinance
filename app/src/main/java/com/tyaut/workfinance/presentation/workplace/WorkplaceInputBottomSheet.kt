package com.tyaut.workfinance.presentation.workplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.tyaut.workfinance.data.db.entity.AccountEntity
import com.tyaut.workfinance.data.db.entity.WorkplaceEntity
import com.tyaut.workfinance.databinding.BottomSheetWorkplaceInputBinding
import com.tyaut.workfinance.domain.enums.BusinessDayRule
import com.tyaut.workfinance.domain.enums.RoundingRule
import com.tyaut.workfinance.domain.enums.WageType

class WorkplaceInputBottomSheet : BottomSheetDialogFragment() {

    /** 編集時は既存データを渡す（新規作成時は null） */
    var existingWorkplace: WorkplaceEntity? = null
    var bankAccounts: List<AccountEntity> = emptyList()
    var onConfirm: ((WorkplaceEntity) -> Unit)? = null

    private var _binding: BottomSheetWorkplaceInputBinding? = null
    private val binding get() = _binding!!

    private var selectedRounding: RoundingRule = RoundingRule.FLOOR
    private var selectedPayDayRule: BusinessDayRule? = null
    private var selectedDepositAccountId: Long = -1L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetWorkplaceInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
        setupWageTypeToggle()
        existingWorkplace?.let { prefill(it) }

        binding.tvTitle.text = if (existingWorkplace == null) "勤務先を追加" else "勤務先を編集"
        binding.btnSave.setOnClickListener { onSaveClick() }
    }

    private fun setupSpinners() {
        // 小数点処理
        val roundingOptions = RoundingRule.entries.map { it.displayName }
        binding.actRoundingRule.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, roundingOptions)
        )
        binding.actRoundingRule.setText(RoundingRule.FLOOR.displayName, false)
        binding.actRoundingRule.setOnItemClickListener { _, _, position, _ ->
            selectedRounding = RoundingRule.entries[position]
        }

        // 給料日の営業日補正
        val ruleOptions = listOf("なし") + BusinessDayRule.entries.map { it.displayName }
        binding.actPayDayRule.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, ruleOptions)
        )
        binding.actPayDayRule.setText("なし", false)
        binding.actPayDayRule.setOnItemClickListener { _, _, position, _ ->
            selectedPayDayRule = if (position == 0) null else BusinessDayRule.entries[position - 1]
        }

        // 振込口座（銀行口座のみ）
        val accountNames = bankAccounts.map { it.name }
        binding.actDepositAccount.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, accountNames)
        )
        if (bankAccounts.isNotEmpty()) {
            selectedDepositAccountId = bankAccounts[0].id
            binding.actDepositAccount.setText(bankAccounts[0].name, false)
        }
        binding.actDepositAccount.setOnItemClickListener { _, _, position, _ ->
            selectedDepositAccountId = bankAccounts[position].id
        }
    }

    private fun setupWageTypeToggle() {
        binding.rgWageType.setOnCheckedChangeListener { _, checkedId ->
            val isHourly = checkedId == binding.rbHourly.id
            binding.tilWage.hint = if (isHourly) "時給（円）" else "月給（円）"
        }
    }

    private fun prefill(wp: WorkplaceEntity) {
        binding.etName.setText(wp.name)
        if (wp.wageType == WageType.MONTHLY) binding.rbMonthly.isChecked = true
        val wageValue = wp.hourlyWage ?: wp.monthlyWage
        wageValue?.let { binding.etWage.setText(it.toString()) }
        binding.etNightPremium.setText(wp.nightPremiumRate.toString())
        binding.actRoundingRule.setText(wp.roundingRule.displayName, false)
        selectedRounding = wp.roundingRule
        binding.etPayDay.setText(wp.payDay.toString())
        wp.payDayBusinessDayRule?.let { rule ->
            binding.actPayDayRule.setText(rule.displayName, false)
            selectedPayDayRule = rule
        }
        binding.etNotifyMinutes.setText(wp.preWorkNotifyMinutes.toString())
        selectedDepositAccountId = wp.depositAccountId
        bankAccounts.indexOfFirst { it.id == wp.depositAccountId }.takeIf { it >= 0 }?.let { idx ->
            binding.actDepositAccount.setText(bankAccounts[idx].name, false)
        }
    }

    private fun onSaveClick() {
        val name = binding.etName.text.toString().trim()
        if (name.isBlank()) { binding.etName.error = "勤務先名を入力してください"; return }
        if (selectedDepositAccountId < 0) {
            Snackbar.make(
                requireView(),
                "振込先口座がありません。先に「口座」タブで銀行口座を作成してください。",
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        val isHourly = binding.rbHourly.isChecked
        val wageAmount = binding.etWage.text.toString().toLongOrNull() ?: 0L

        val entity = WorkplaceEntity(
            id = existingWorkplace?.id ?: 0L,
            name = name,
            wageType = if (isHourly) WageType.HOURLY else WageType.MONTHLY,
            hourlyWage = if (isHourly) wageAmount else null,
            monthlyWage = if (!isHourly) wageAmount else null,
            nightPremiumRate = binding.etNightPremium.text.toString().toDoubleOrNull() ?: 1.25,
            roundingRule = selectedRounding,
            payDay = binding.etPayDay.text.toString().toIntOrNull() ?: 25,
            payDayBusinessDayRule = selectedPayDayRule,
            depositAccountId = selectedDepositAccountId,
            preWorkNotifyMinutes = binding.etNotifyMinutes.text.toString().toIntOrNull() ?: 30,
            isActive = existingWorkplace?.isActive ?: true
        )
        onConfirm?.invoke(entity)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
