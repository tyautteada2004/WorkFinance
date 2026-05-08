package com.tyaut.workfinance.domain.enums

import java.math.RoundingMode

enum class RoundingRule(val displayName: String, val roundingMode: RoundingMode) {
    CEILING("切り上げ", RoundingMode.CEILING),
    FLOOR("切り捨て", RoundingMode.FLOOR),
    HALF_UP("四捨五入", RoundingMode.HALF_UP);
}
