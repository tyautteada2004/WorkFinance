package com.tyaut.workfinance.util

import java.text.NumberFormat
import java.util.Locale

object CurrencyFormatter {

    private val fmt = NumberFormat.getCurrencyInstance(Locale.JAPAN)

    fun format(yen: Long): String = fmt.format(yen)

    /** 正値は緑、負値は赤のラベル文字列（符号付き） */
    fun formatSigned(yen: Long): String =
        if (yen >= 0) "+${format(yen)}" else format(yen)
}
