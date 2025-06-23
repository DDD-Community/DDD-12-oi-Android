package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color

@Immutable
class OiCalendarColors (
    val containerColor: Color,
    val weekdayContentColor: Color,
    private val dayContentColor: Color,
    private val disabledDayContentColor: Color,
    private val selectedDayContainerColor: Color,
    private val selectedDayContentColor: Color,
    private val todayContainerColor: Color,
    private val todayContentColor: Color,
    val rangeBackgroundColor: Color,
    private val isRangeModel: Boolean
) {
    @Composable
    internal fun dayContentColor(
        isToday: Boolean,
        isRange: Boolean,
        isSelected: Boolean,
        enabled: Boolean
    ): State<Color> {
        val target =
            when {
                isRangeModel && (isRange || isSelected) -> selectedDayContentColor
                (isSelected && enabled) -> selectedDayContentColor
                isToday -> todayContentColor
                enabled -> dayContentColor
                else -> disabledDayContentColor
            }
        return animateColorAsState(target, tween(durationMillis = 100))
    }

    @Composable
    internal fun dayContainerColor(
        isToday: Boolean,
        isSelected: Boolean,
        isRange: Boolean,
        animate: Boolean
    ): State<Color> {
        val target = when {
            isRangeModel && isRange -> Color.Transparent
            isRangeModel && isSelected -> rangeBackgroundColor
            isSelected -> selectedDayContainerColor
            isToday -> todayContainerColor
            else -> Color.Transparent
        }
        return if (animate) {
            animateColorAsState(target, tween(durationMillis = 100))
        } else {
            rememberUpdatedState(target)
        }
    }
}
