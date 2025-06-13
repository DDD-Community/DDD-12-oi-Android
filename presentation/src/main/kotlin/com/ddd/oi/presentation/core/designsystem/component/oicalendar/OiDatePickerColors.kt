package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

@Immutable
class OiDatePickerColors
constructor(
    private val containerColor: Color,
    private val weekdayContentColor: Color,
    val dayContentColor: Color,
    private val disabledSelectedDayContentColor: Color,
    private val disabledDayContentColor: Color,
    private val selectedDayContainerColor: Color,
    private val selectedDayContentColor: Color,
    private val todayContainerColor: Color,
    private val todayContentColor: Color,
) {
    fun copy(
        containerColor: Color = this.containerColor,
        weekdayContentColor: Color = this.weekdayContentColor,
        dayContentColor: Color = this.dayContentColor,
        disabledSelectedDayContentColor: Color = this.disabledSelectedDayContentColor,
        disabledDayContentColor: Color = this.disabledDayContentColor,
        selectedDayContainerColor: Color = this.selectedDayContainerColor,
        selectedDayContentColor: Color = this.selectedDayContentColor,
        todayContainerColor: Color = this.todayContainerColor,
        todayContentColor: Color = this.todayContentColor,
    ) = OiDatePickerColors(
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

    @Composable
    internal fun dayContentColor(
        isToday: Boolean,
        selected: Boolean,
        enabled: Boolean
    ): State<Color> {
        val target =
            when {
                selected && enabled -> selectedDayContentColor
                selected && !enabled -> disabledSelectedDayContentColor
                isToday -> todayContentColor
                enabled -> dayContentColor
                else -> disabledDayContentColor
            }
        return animateColorAsState(target, tween(durationMillis = 100))
    }

    @Composable
    internal fun dayContainerColor(
        today: Boolean,
        selected: Boolean,
        enabled: Boolean,
        animate: Boolean
    ): State<Color> {
        val target = when {
            today && selected -> selectedDayContainerColor
            today && !selected -> Color.Gray
            selected && enabled -> selectedDayContainerColor
            selected && !enabled -> Color.Green
            else -> Color.Transparent
        }
        return if (animate) {
            animateColorAsState(target, tween(durationMillis = 100))
        } else {
            rememberUpdatedState(target)
        }
    }
}
