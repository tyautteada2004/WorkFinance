package com.tyaut.workfinance.presentation.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tyaut.workfinance.databinding.FragmentAccountBinding
import com.tyaut.workfinance.domain.enums.AccountType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AccountViewModel by viewModels()

    private val accountAdapter = AccountAdapter(onAccountClick = ::onAccountClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAccounts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = accountAdapter
        }

        binding.fabAddAccount.setOnClickListener { showCreateAccountTypeDialog() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.accountsWithBalance.collect { items ->
                    accountAdapter.submitList(items)
                }
            }
        }
    }

    private fun onAccountClick(item: AccountWithBalance) {
        // 初期残高は変更不可 — 名前・設定のみ変更できるダイアログを表示
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(item.account.name)
            .setMessage(
                "種別: ${item.account.type.displayName}\n" +
                "初期残高: ¥${item.account.initialBalance}（変更不可）"
            )
            .setPositiveButton("閉じる", null)
            .show()
    }

    /** Step1: 口座種別を選択 */
    private fun showCreateAccountTypeDialog() {
        val types = AccountType.entries.map { it.displayName }.toTypedArray()
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("口座の種別を選択")
            .setItems(types) { _, which ->
                showCreateAccountDetailDialog(AccountType.entries[which])
            }
            .show()
    }

    /** Step2: 名前・初期残高を入力（クレカは締め日・支払日も入力） */
    private fun showCreateAccountDetailDialog(type: AccountType) {
        AccountCreateBottomSheet().apply {
            accountType = type
            onConfirm = { name, initialBalance, closingDay, paymentDay, businessDayRule ->
                viewModel.createAccount(name, type, initialBalance, closingDay, paymentDay, businessDayRule)
            }
        }.show(childFragmentManager, "create_account")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
