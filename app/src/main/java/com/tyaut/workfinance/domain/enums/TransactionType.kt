package com.tyaut.workfinance.domain.enums

enum class TransactionType(val displayName: String) {
    EXPENSE("支出"),
    INCOME("収入"),
    TRANSFER("振替"),
    BALANCE_ADJUST("残高調整");
}
