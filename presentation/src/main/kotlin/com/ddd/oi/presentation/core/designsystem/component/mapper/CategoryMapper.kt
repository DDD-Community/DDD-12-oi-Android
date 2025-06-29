package com.ddd.oi.presentation.core.designsystem.component.mapper

import androidx.compose.ui.graphics.Color
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.indigo400
import com.ddd.oi.presentation.core.designsystem.theme.lime400
import com.ddd.oi.presentation.core.designsystem.theme.rose400
import com.ddd.oi.presentation.core.designsystem.theme.teal400
import com.ddd.oi.presentation.core.designsystem.theme.yellow400
import com.ddd.oi.presentation.schedule.contract.CategoryUi

internal fun Category.getColor(): Color = when (this) {
    Category.Daily -> yellow400
    Category.Date -> rose400
    Category.Travel -> teal400
    Category.Business -> indigo400
    Category.Etc -> lime400
}

internal fun CategoryUi.getCategoryName(): Int = when (this) {
    CategoryUi.Daily -> R.string.daily
    CategoryUi.Travel -> R.string.travel
    CategoryUi.Date -> R.string.date
    CategoryUi.Business -> R.string.business
    CategoryUi.Etc -> R.string.etc
}

internal fun Category.toUi(): CategoryUi {
    return when(this) {
        Category.Travel -> CategoryUi.Travel
        Category.Date -> CategoryUi.Date
        Category.Daily -> CategoryUi.Daily
        Category.Business -> CategoryUi.Business
        Category.Etc -> CategoryUi.Etc
    }
}