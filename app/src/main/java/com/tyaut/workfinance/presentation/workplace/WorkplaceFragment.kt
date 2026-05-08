package com.tyaut.workfinance.presentation.workplace

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
import com.tyaut.workfinance.data.db.entity.WorkplaceEntity
import com.tyaut.workfinance.databinding.FragmentWorkplaceBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WorkplaceFragment : Fragment() {

    private var _binding: FragmentWorkplaceBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WorkplaceViewModel by viewModels()

    private val workplaceAdapter = WorkplaceAdapter(onWorkplaceClick = ::onWorkplaceClick)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkplaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvWorkplaces.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workplaceAdapter
        }

        binding.fabAddWorkplace.setOnClickListener { showAddWorkplaceDialog() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.workplaces.collect { workplaces ->
                    workplaceAdapter.submitList(workplaces)
                }
            }
        }
    }

    private fun onWorkplaceClick(workplace: WorkplaceEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(workplace.name)
            .setItems(arrayOf("編集する", "無効化する")) { _, which ->
                when (which) {
                    0 -> showEditWorkplaceDialog(workplace)
                    1 -> confirmDeactivate(workplace)
                }
            }
            .show()
    }

    private fun showAddWorkplaceDialog() {
        WorkplaceInputBottomSheet().apply {
            bankAccounts = viewModel.bankAccounts.value
            onConfirm = { entity -> viewModel.save(entity) }
        }.show(childFragmentManager, "add_workplace")
    }

    private fun showEditWorkplaceDialog(workplace: WorkplaceEntity) {
        WorkplaceInputBottomSheet().apply {
            existingWorkplace = workplace
            bankAccounts = viewModel.bankAccounts.value
            onConfirm = { entity -> viewModel.save(entity) }
        }.show(childFragmentManager, "edit_workplace")
    }

    private fun confirmDeactivate(workplace: WorkplaceEntity) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("無効化確認")
            .setMessage("「${workplace.name}」を無効化しますか？\nシフト履歴は保持されます。")
            .setPositiveButton("無効化") { _, _ -> viewModel.deactivate(workplace.id) }
            .setNegativeButton("キャンセル", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
