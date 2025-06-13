package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

@ExperimentalMaterial3Api
@Stable
object OiDatePickerDefaults {

    @Composable
    fun colors(
        containerColor: Color = Color.White,
        weekdayContentColor: Color = OiTheme.colors.textPrimary,
        dayContentColor: Color = OiTheme.colors.textSecondary,
        disabledSelectedDayContentColor: Color = OiTheme.colors.textDisabled,
        disabledDayContentColor: Color = OiTheme.colors.textDisabled,
        selectedDayContainerColor: Color = OiTheme.colors.backgroundBrand,
        selectedDayContentColor: Color = OiTheme.colors.textOnPrimary,
        todayContainerColor: Color = OiTheme.colors.backgroundSecondary,
        todayContentColor: Color = OiTheme.colors.textOnPrimary,
    ): OiDatePickerColors = OiDatePickerColors(
        containerColor,
        weekdayContentColor,
        dayContentColor,
        disabledSelectedDayContentColor,
        disabledDayContentColor,
        selectedDayContainerColor,
        selectedDayContentColor,
        todayContainerColor,
        todayContentColor
    )
}