package com.tyaut.workfinance.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tyaut.workfinance.databinding.FragmentDashboardBinding
import com.tyaut.workfinance.util.CurrencyFormatter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DashboardViewModel by viewModels()

    private val forecastAdapter = ForecastAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvForecast.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = forecastAdapter
            setHasFixedSize(false)
        }

        binding.swipeRefresh.setOnRefreshListener { viewModel.loadDashboard() }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.netWorth.collect { snapshot ->
                        snapshot ?: return@collect
                        binding.tvNetWorth.text = CurrencyFormatter.format(snapshot.netWorth)
                        binding.tvTotalAssets.text = CurrencyFormatter.format(snapshot.totalAssets)
                        binding.tvTotalLiabilities.text =
                            CurrencyFormatter.format(snapshot.totalLiabilities)
                    }
                }
                launch {
                    viewModel.forecast.collect { entries ->
                        forecastAdapter.submitList(entries)
                        binding.tvDeficitWarning.isVisible = entries.any { it.isDeficit }
                    }
                }
                launch {
                    viewModel.isLoading.collect { loading ->
                        binding.swipeRefresh.isRefreshing = loading
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
