package com.ddd.oi.presentation.core.designsystem.component.mapper

import androidx.compose.ui.graphics.Color

internal fun getPlaceCategoryColor(category: String): Color {
    return when (category) {
        "음식점" -> Color(0xFFF76945)
        "카페" -> Color(0xFF09B596)
        "관광명소" -> Color(0xFFF7A61F)
        "숙박시설" -> Color(0xFFA052FF)
        "편의시설" -> Color(0xFF5F77FF)
        "기타" -> Color(0xFF676767)
        else -> Color(0xFFE5E5E5)
    }
}