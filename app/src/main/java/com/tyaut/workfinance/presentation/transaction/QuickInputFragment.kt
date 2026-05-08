package com.tyaut.workfinance.presentation.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.tyaut.workfinance.R
import com.tyaut.workfinance.databinding.FragmentQuickInputBinding
import com.tyaut.workfinance.domain.enums.ExpenseCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuickInputFragment : Fragment() {

    private var _binding: FragmentQuickInputBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TransactionViewModel by viewModels()

    private var selectedCategory: ExpenseCategory = ExpenseCategory.CONSUMPTION

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQuickInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryChips()
        setupConfirmButton()
    }

    private fun setupCategoryChips() {
        binding.chipGroupCategory.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedCategory = when (checkedIds.firstOrNull()) {
                R.id.chip_consumption -> ExpenseCategory.CONSUMPTION
                R.id.chip_investment -> ExpenseCategory.INVESTMENT
                R.id.chip_waste -> ExpenseCategory.WASTE
                else -> ExpenseCategory.CONSUMPTION
            }
        }
    }

    private fun setupConfirmButton() {
        binding.btnConfirm.setOnClickListener {
            val amountText = binding.etAmount.text.toString()
            val amount = amountText.toLongOrNull() ?: return@setOnClickListener
            val description = binding.etDescription.text.toString().ifBlank { selectedCategory.displayName }
            val accountId = viewModel.accounts.value.firstOrNull()?.id ?: return@setOnClickListener

            viewModel.addExpense(accountId, amount, selectedCategory, description)
            clearInput()
        }
    }

    private fun clearInput() {
        binding.etAmount.text?.clear()
        binding.etDescription.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
