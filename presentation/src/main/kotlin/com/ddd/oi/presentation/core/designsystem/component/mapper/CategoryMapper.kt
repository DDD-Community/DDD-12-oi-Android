package com.ddd.oi.presentation.core.designsystem.component.mapper

import androidx.compose.ui.graphics.Color
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.presentation.R
import com.ddd.oi.presentation.core.designsystem.theme.indigo400
import com.ddd.oi.presentation.core.designsystem.theme.lime400
import com.ddd.oi.presentation.core.designsystem.theme.rose400
import com.ddd.oi.presentation.core.designsystem.theme.teal400
import com.ddd.oi.presentation.core.designsystem.theme.yellow400

internal fun Category.getColor(): Color = when (this) {
    Category.Daily -> yellow400
    Category.Date -> rose400
    Category.Travel -> teal400
    Category.Business -> indigo400
    Category.Etc -> lime400
}

internal fun Category.getCategoryIcon(): Int = when (this) {
    Category.Daily -> R.drawable.ic_daily
    Category.Travel -> R.drawable.ic_travel
    Category.Date -> R.drawable.ic_date
    Category.Business -> R.drawable.ic_business
    Category.Etc -> R.drawable.ic_horizontalmore
}

internal fun Category.getCategoryName(): Int = when (this) {
    Category.Daily -> R.string.daily
    Category.Travel -> R.string.travel
    Category.Date -> R.string.date
    Category.Business -> R.string.business
    Category.Etc -> R.string.etc
}