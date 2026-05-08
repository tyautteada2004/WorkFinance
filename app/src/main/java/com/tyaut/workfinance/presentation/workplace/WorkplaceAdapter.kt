package com.tyaut.workfinance.presentation.workplace

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tyaut.workfinance.data.db.entity.WorkplaceEntity
import com.tyaut.workfinance.databinding.ItemWorkplaceBinding
import com.tyaut.workfinance.domain.enums.WageType
import com.tyaut.workfinance.util.CurrencyFormatter

class WorkplaceAdapter(
    private val onWorkplaceClick: (WorkplaceEntity) -> Unit
) : ListAdapter<WorkplaceEntity, WorkplaceAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemWorkplaceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(workplace: WorkplaceEntity) {
            binding.root.setOnClickListener { onWorkplaceClick(workplace) }

            binding.tvWorkplaceName.text = workplace.name
            binding.tvWageType.text = workplace.wageType.displayName

            binding.tvWageInfo.text = buildString {
                when (workplace.wageType) {
                    WageType.HOURLY  -> {
                        val wage = workplace.hourlyWage?.let { CurrencyFormatter.format(it) } ?: "未設定"
                        append("時給 $wage")
                    }
                    WageType.MONTHLY -> {
                        val wage = workplace.monthlyWage?.let { CurrencyFormatter.format(it) } ?: "未設定"
                        append("月給 $wage")
                    }
                }
                append(" / 給料日: ${workplace.payDay}日")
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<WorkplaceEntity>() {
        override fun areItemsTheSame(oldItem: WorkplaceEntity, newItem: WorkplaceEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: WorkplaceEntity, newItem: WorkplaceEntity) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemWorkplaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}
