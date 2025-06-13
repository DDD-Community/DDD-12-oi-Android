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

object OiButtonDimens {
    val smallHeight = 32.dp
    val mediumHeight = 40.dp
    val largeHeight = 48.dp
    val xlargeHeight = 56.dp

    val roundedRectRadius = 8.dp
    val ovalRadius = 999.dp

    val componentMargin = 4.dp
}

object OiChipDimens {
    val iconSize = 14.dp
    val height = 32.dp

    val horizontalPadding = 12.dp
    val verticalPadding = 7.dp

    val ovalRadius = 999.dp

    val componentMargin = 2.dp
}

object OiTextFieldDimens {
    val iconSize = 20.dp
    val height = 48.dp
    val roundedRectRadius = 8.dp

    val horizontalPadding = 16.dp
    val componentMargin = 8.dp

    val stroke = 1.dp
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