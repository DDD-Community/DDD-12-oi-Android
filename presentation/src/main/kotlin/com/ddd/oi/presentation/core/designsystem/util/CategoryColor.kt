package com.ddd.oi.presentation.core.designsystem.util

import androidx.compose.ui.graphics.Color
import com.ddd.oi.domain.model.Category
import com.ddd.oi.presentation.core.designsystem.theme.indigo400
import com.ddd.oi.presentation.core.designsystem.theme.lime400
import com.ddd.oi.presentation.core.designsystem.theme.rose400
import com.ddd.oi.presentation.core.designsystem.theme.teal400
import com.ddd.oi.presentation.core.designsystem.theme.yellow400
import com.ddd.oi.presentation.schedule.model.UiCategory

fun UiCategory.getColor(): Color =
    when (this) {
        UiCategory.DAILY -> yellow400
        UiCategory.DATE -> yellow400
        UiCategory.TRAVEL -> teal400
        UiCategory.BUSINESS -> indigo400
        UiCategory.ETC -> lime400
    }