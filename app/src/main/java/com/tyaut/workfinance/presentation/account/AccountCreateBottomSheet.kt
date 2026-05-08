package com.tyaut.workfinance.presentation.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tyaut.workfinance.databinding.BottomSheetAccountCreateBinding
import com.tyaut.workfinance.domain.enums.AccountType
import com.tyaut.workfinance.domain.enums.BusinessDayRule

class AccountCreateBottomSheet : BottomSheetDialogFragment() {

    var accountType: AccountType = AccountType.BANK
    var onConfirm: ((name: String, initialBalance: Long, closingDay: Int?, paymentDay: Int?, businessDayRule: BusinessDayRule?) -> Unit)? = null

    private var _binding: BottomSheetAccountCreateBinding? = null
    private val binding get() = _binding!!

    private var selectedBusinessDayRule: BusinessDayRule? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetAccountCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTitle.text = "${accountType.displayName}を追加"

        // クレカのみ締め日・支払日を表示
        binding.sectionCreditCard.isVisible = (accountType == AccountType.CREDIT_CARD)

        setupBusinessDayRuleSpinner()

        binding.btnCreate.setOnClickListener { onCreateClick() }
    }

    private fun setupBusinessDayRuleSpinner() {
        val options = listOf("なし") + BusinessDayRule.entries.map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, options)
        binding.actBusinessDayRule.setAdapter(adapter)
        binding.actBusinessDayRule.setText("なし", false)
        binding.actBusinessDayRule.setOnItemClickListener { _, _, position, _ ->
            selectedBusinessDayRule = if (position == 0) null
                                      else BusinessDayRule.entries[position - 1]
        }
    }

    private fun onCreateClick() {
        val name = binding.etAccountName.text.toString().trim()
        if (name.isBlank()) {
            binding.etAccountName.error = "口座名を入力してください"
            return
        }
        val balance = binding.etInitialBalance.text.toString().toLongOrNull() ?: 0L
        val closingDay = binding.etClosingDay.text.toString().toIntOrNull()
        val paymentDay = binding.etPaymentDay.text.toString().toIntOrNull()

        onConfirm?.invoke(name, balance, closingDay, paymentDay, selectedBusinessDayRule)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
