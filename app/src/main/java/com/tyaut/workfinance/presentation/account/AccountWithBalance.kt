package com.tyaut.workfinance.presentation.account

import com.tyaut.workfinance.data.db.entity.AccountEntity

data class AccountWithBalance(
    val account: AccountEntity,
    val currentBalance: Long
)
