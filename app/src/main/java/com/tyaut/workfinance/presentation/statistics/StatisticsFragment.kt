package com.tyaut.workfinance.presentation.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.tyaut.workfinance.databinding.FragmentStatisticsBinding
import com.tyaut.workfinance.util.CurrencyFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.YearMonth

@AndroidEntryPoint
class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMonth(YearMonth.now())

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stats.collect { stats ->
                    stats ?: return@collect
                    binding.tvTotalIncome.text = CurrencyFormatter.format(stats.totalIncome)
                    binding.tvTotalExpense.text = CurrencyFormatter.format(stats.totalExpense)
                    updatePieChart(stats)
                }
            }
        }
    }

    private fun updatePieChart(stats: CategoryStats) {
        val entries = listOf(
            PieEntry(stats.consumption.toFloat(), "消費"),
            PieEntry(stats.investment.toFloat(), "投資"),
            PieEntry(stats.waste.toFloat(), "浪費")
        ).filter { it.value > 0 }

        if (entries.isEmpty()) return

        val dataSet = PieDataSet(entries, "支出分類")
        dataSet.colors = listOf(
            android.graphics.Color.parseColor("#4CAF50"),
            android.graphics.Color.parseColor("#2196F3"),
            android.graphics.Color.parseColor("#F44336")
        )
        binding.pieChart.data = PieData(dataSet)
        binding.pieChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
