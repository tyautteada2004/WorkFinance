package com.tyaut.workfinance.presentation.account

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tyaut.workfinance.databinding.ItemAccountBinding
import com.tyaut.workfinance.domain.enums.AccountType
import com.tyaut.workfinance.util.CurrencyFormatter

class AccountAdapter(
    private val onAccountClick: (AccountWithBalance) -> Unit
) : ListAdapter<AccountWithBalance, AccountAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemAccountBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AccountWithBalance) {
            binding.root.setOnClickListener { onAccountClick(item) }

            binding.tvAccountName.text = item.account.name
            binding.tvAccountType.text = item.account.type.displayName

            binding.tvBalance.text = CurrencyFormatter.format(item.currentBalance)

            // 負債系は残高がマイナス表示になるため赤文字
            val isNegative = item.currentBalance < 0
            binding.tvBalance.setTextColor(
                if (isNegative) Color.parseColor("#B00020") else Color.parseColor("#1D1B20")
            )

            // 口座種別に応じてカードの左枠線色を変える（参考用）
            val accentColor = when (item.account.type) {
                AccountType.CASH        -> Color.parseColor("#4CAF50")
                AccountType.BANK        -> Color.parseColor("#2196F3")
                AccountType.CREDIT_CARD -> Color.parseColor("#FF9800")
                AccountType.LOAN        -> Color.parseColor("#F44336")
            }
            binding.root.strokeColor = accentColor
            binding.root.strokeWidth = 3
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<AccountWithBalance>() {
        override fun areItemsTheSame(oldItem: AccountWithBalance, newItem: AccountWithBalance) =
            oldItem.account.id == newItem.account.id

        override fun areContentsTheSame(oldItem: AccountWithBalance, newItem: AccountWithBalance) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}
