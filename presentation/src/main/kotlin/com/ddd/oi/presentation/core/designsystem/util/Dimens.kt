package com.ddd.oi.presentation.core.designsystem.util

import androidx.compose.ui.unit.dp

object Dimens {
    val paddingXSmall = 4.dp
    val paddingSmall = 8.dp
    val paddingMediumXSmall = 10.dp
    val paddingMediumSmall = 12.dp
    val paddingMedium = 16.dp
    val paddingMediumLarge = 20.dp
    val paddingLargeSmall = 24.dp
    val paddingLargeMedium = 28.dp
    val paddingLarge = 32.dp
    val fabSize = 48.dp
    val fabOffset = (fabSize / 2) + paddingMedium
    val largeHeight = 40.dp
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
    object RoundRect {
        val iconSize = 14.dp
        val height = 32.dp

        val horizontalPadding = 12.dp
        val verticalPadding = 7.dp

        val ovalRadius = 999.dp

        val componentMargin = 2.dp
    }

    object Oval {
        val iconSize = 24.dp
        val size = 52.dp

        val padding = 14.dp

        val borderStroke = 1.dp

        val componentMargin = 12.dp
    }

    object Choice {
        val height = 40.dp

        val horizontalPadding = 16.dp
        val verticalPadding = 11.dp
    }
}

object OiChipTabDimes {
    object RoundRect {
        val horizontalPadding = 16.dp
        val verticalPadding = 12.dp
    }
    object Oval {
        val verticalPadding = 13.5.dp
        val horizontalPadding = 17.dp
    }
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
    val bottomBarHeight = 56.dp
}

object BottomBarShapeDimens {
    val width = 150.dp
    val height = 90.dp
    val curveStart = 75.dp
    val curveEnd = 85.dp
}

object OiCalendarDimens {
    val calendarCellHeight = 34.dp
    val dayWidth = 28.dp
    val weekDayTopPadding = 4.dp
    val categoryCircleSize = 4.dp
    val categoryPadding = 2.dp
}

object MonthSelectorDimens {
    val height = 56.dp
    val horizontalPadding = 16.dp
    val verticalPadding = 14.5.dp
    val dropDownStartPadding = 2.dp
}

object OiCardDimens {
    val cardHeight = 70.dp
    val cornerRadius = 16.dp
    val lineWidth = 4.dp
    val lineHeight = 38.dp
    val dividerHeight = 8.dp
    val moreIconSize = 16.dp
    val elevation = 2.dp
}

object DialogDimens {
    val cornerRadius = 24.dp
}

object MonthGridDimens {
    val horizontalPadding = 45.17.dp
    var verticalPadding = 23.dp
}

object OiSnackBarDimen {
    val snackbarHeight = 48.dp
    val cornerRadius = 12.dp
}