package com.ddd.oi.presentation.core.designsystem.util

import androidx.compose.ui.graphics.Color
import com.ddd.oi.domain.model.Category
import com.ddd.oi.presentation.core.designsystem.theme.indigo400
import com.ddd.oi.presentation.core.designsystem.theme.lime400
import com.ddd.oi.presentation.core.designsystem.theme.teal400
import com.ddd.oi.presentation.core.designsystem.theme.yellow400

fun Category.getColor(): Color =
    when (this) {
        Category.Daily -> yellow400
        Category.Date -> yellow400
        Category.Travel -> teal400
        Category.Business -> indigo400
        Category.Etc -> lime400
    }