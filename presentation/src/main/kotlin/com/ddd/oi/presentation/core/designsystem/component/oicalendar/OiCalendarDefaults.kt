package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.theme.white
import java.time.ZoneId

@Stable
object OiCalendarDefaults {

    private const val ZONE_ID = "UTC"

    val zone: ZoneId = ZoneId.of(ZONE_ID)

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