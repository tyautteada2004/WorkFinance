package com.tyaut.workfinance.presentation.shift

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tyaut.workfinance.R
import com.tyaut.workfinance.data.db.entity.ShiftEntity
import com.tyaut.workfinance.databinding.ItemShiftBinding
import com.tyaut.workfinance.domain.enums.ShiftStatus
import com.tyaut.workfinance.util.CurrencyFormatter
import com.tyaut.workfinance.util.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class ShiftAdapter(
    private val onShiftClick: (ShiftEntity) -> Unit
) : ListAdapter<ShiftEntity, ShiftAdapter.ViewHolder>(DiffCallback()) {

    private val dtFormatter = DateTimeFormatter.ofPattern("M/d(E) HH:mm", Locale.JAPAN)
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", Locale.JAPAN)

    inner class ViewHolder(private val binding: ItemShiftBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(shift: ShiftEntity) {
            binding.root.setOnClickListener { onShiftClick(shift) }

            // ステータスチップ
            binding.tvStatus.text = shift.status.displayName
            val chipColor = when (shift.status) {
                ShiftStatus.SCHEDULED -> Color.parseColor("#90A4AE")
                ShiftStatus.ACTUAL    -> Color.parseColor("#FFA726")
                ShiftStatus.CONFIRMED -> Color.parseColor("#66BB6A")
            }
            DrawableCompat.wrap(binding.tvStatus.background).mutate()
                .setTint(chipColor)

            binding.tvWorkplace.text = shift.workplaceName

            // 日時表示: 予定 or 実績ベースで切り替え
            val start = (shift.actualStart ?: shift.scheduledStart).toLocalDateTime()
            val end = (shift.actualEnd ?: shift.scheduledEnd).toLocalDateTime()
            binding.tvTimeRange.text = buildString {
                append(start.format(dtFormatter))
                append(" 〜 ")
                append(end.format(timeFormatter))
                if (shift.breakMinutes > 0) append(" (休憩${shift.breakMinutes}分)")
            }

            // 賃金: ステータスに応じて表示する値を切り替え
            val wage = when (shift.status) {
                ShiftStatus.SCHEDULED -> shift.scheduledWage
                ShiftStatus.ACTUAL    -> shift.actualWage
                ShiftStatus.CONFIRMED -> shift.confirmedWage
            }
            binding.tvWage.text = wage?.let { CurrencyFormatter.format(it) } ?: "---"
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ShiftEntity>() {
        override fun areItemsTheSame(oldItem: ShiftEntity, newItem: ShiftEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ShiftEntity, newItem: ShiftEntity) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemShiftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}
