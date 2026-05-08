package com.tyaut.workfinance.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tyaut.workfinance.R
import com.tyaut.workfinance.databinding.ItemForecastBinding
import com.tyaut.workfinance.domain.model.ForecastEntry
import com.tyaut.workfinance.util.CurrencyFormatter
import java.time.format.DateTimeFormatter
import java.util.Locale

class ForecastAdapter : ListAdapter<ForecastEntry, ForecastAdapter.ViewHolder>(DiffCallback()) {

    private val dateFormatter = DateTimeFormatter.ofPattern("M/d", Locale.JAPAN)
    private val dayOfWeekFormatter = DateTimeFormatter.ofPattern("(E)", Locale.JAPAN)

    inner class ViewHolder(private val binding: ItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: ForecastEntry) {
            binding.tvDate.text = entry.date.format(dateFormatter)
            binding.tvDayOfWeek.text = entry.date.format(dayOfWeekFormatter)

            val ctx = binding.root.context
            binding.tvBalance.text = CurrencyFormatter.format(entry.balance)
            binding.tvBalance.setTextColor(
                if (entry.isDeficit) ContextCompat.getColor(ctx, R.color.colorError)
                else ContextCompat.getColor(ctx, R.color.colorOnSurface)
            )

            // 当日のイベントを「名称 ±金額」でカンマ区切り表示
            binding.tvEvents.text = entry.events.joinToString("  ") { event ->
                "${event.label} ${CurrencyFormatter.formatSigned(event.amount)}"
            }

            // 赤字予定日はカード背景を薄赤に
            binding.root.setCardBackgroundColor(
                if (entry.isDeficit) ContextCompat.getColor(ctx, R.color.colorErrorContainer)
                else ContextCompat.getColor(ctx, R.color.colorSurface)
            )
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ForecastEntry>() {
        override fun areItemsTheSame(oldItem: ForecastEntry, newItem: ForecastEntry) =
            oldItem.date == newItem.date

        override fun areContentsTheSame(oldItem: ForecastEntry, newItem: ForecastEntry) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}
