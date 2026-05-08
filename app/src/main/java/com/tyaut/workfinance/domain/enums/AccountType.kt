package com.tyaut.workfinance.domain.enums

enum class AccountType(val displayName: String) {
    CASH("現金"),
    BANK("銀行口座"),
    CREDIT_CARD("クレジットカード"),
    LOAN("奨学金・借入");

    val isAsset: Boolean get() = this == CASH || this == BANK
    val isLiability: Boolean get() = this == CREDIT_CARD || this == LOAN
}
