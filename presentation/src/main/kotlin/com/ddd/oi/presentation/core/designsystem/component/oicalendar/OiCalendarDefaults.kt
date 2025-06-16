package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white

@Stable
object OiCalendarDefaults {

    @Composable
    fun colors(
        containerColor: Color = white,
        weekdayContentColor: Color = OiTheme.colors.textPrimary,
        dayContentColor: Color = OiTheme.colors.textSecondary,
        disabledDayContentColor: Color = OiTheme.colors.textDisabled,
        selectedDayContainerColor: Color = OiTheme.colors.backgroundPrimary,
        selectedDayContentColor: Color = white,
        todayContainerColor: Color = OiTheme.colors.backgroundUnselected,
        todayContentColor: Color = OiTheme.colors.textPrimary,
    ): OiCalendarColors = OiCalendarColors(
        containerColor,
        weekdayContentColor,
        dayContentColor,
        disabledDayContentColor,
        selectedDayContainerColor,
        selectedDayContentColor,
        todayContainerColor,
        todayContentColor
    )
}