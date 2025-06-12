package com.ddd.oi.presentation.core.designsystem.util

import androidx.compose.ui.unit.dp

object Dimens {
    val paddingXSmall = 4.dp
    val paddingSmall = 8.dp
    val paddingMediumSmall = 12.dp
    val paddingMedium = 16.dp
    val paddingMediumLarge = 20.dp
    val paddingLargeSmall = 24.dp
    val paddingLargeMedium = 28.dp
    val paddingLarge = 32.dp
    val fabSize = 48.dp
    val fabOffset = (fabSize / 2) + paddingMedium
}

object OiHeaderDimens {
    val size = 60.dp
    private val iconSize = 24.dp

    val padding = (size - iconSize).div(2)
}

object BottomBarDimens {
    val bottomBarHeight = 40.dp
}

object BottomBarShapeDimens {
    val width = 150.dp
    val height = 90.dp
    val curveStart = 75.dp
    val curveEnd = 85.dp
}