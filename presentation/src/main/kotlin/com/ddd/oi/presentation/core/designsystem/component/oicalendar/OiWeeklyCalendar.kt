package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.remember
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import java.time.format.TextStyle

@Composable
fun OiWeeklyCalendar(
    modifier: Modifier = Modifier,
    today: LocalDate,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        listOf(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY, 
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
        ).forEachIndexed { index, dayOfWeek ->
            val sundayOffset = if (today.dayOfWeek == DayOfWeek.SUNDAY) 0 else 7 - today.dayOfWeek.ordinal
            val currentWeekStartDate = today.minus(sundayOffset, DateTimeUnit.DAY)
            val clickableDate = currentWeekStartDate.plus(index, DateTimeUnit.DAY)
            
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        shape = CircleShape,
                        color = getBackgroundColor(
                            today = today,
                            selectedDate = selectedDate,
                            dayOfWeek = dayOfWeek,
                        )
                    )
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onDateSelected(clickableDate)
                    },
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = dayOfWeek.getDisplayName(TextStyle.NARROW, getCurrentLocale()),
                    color = getTextColor(
                        today = today,
                        selectedDate = selectedDate,
                        dayOfWeek = dayOfWeek,
                    ),
                    style = OiTheme.typography.bodyMediumSemibold
                )
            }
        }
    }
}

@Composable
private fun getBackgroundColor(
    today: LocalDate,
    selectedDate: LocalDate,
    dayOfWeek: DayOfWeek
): Color {
    val sundayOffset = if (today.dayOfWeek == DayOfWeek.SUNDAY) 0 else 7 - today.dayOfWeek.ordinal
    val currentWeekStartDate = today.minus(sundayOffset, DateTimeUnit.DAY)
    val targetDateForDayOfWeek = currentWeekStartDate.plus(
        when (dayOfWeek) {
            DayOfWeek.SUNDAY -> 0
            DayOfWeek.MONDAY -> 1
            DayOfWeek.TUESDAY -> 2
            DayOfWeek.WEDNESDAY -> 3
            DayOfWeek.THURSDAY -> 4
            DayOfWeek.FRIDAY -> 5
            DayOfWeek.SATURDAY -> 6
        }, 
        DateTimeUnit.DAY
    )
    
    return when(targetDateForDayOfWeek) {
        today -> OiTheme.colors.backgroundPrimary
        selectedDate -> Color(0xFFEDEDED)
        else -> Color.Transparent
    }
}

@Composable
private fun getTextColor(
    today: LocalDate,
    selectedDate: LocalDate,
    dayOfWeek: DayOfWeek
): Color {
    val sundayOffset = if (today.dayOfWeek == DayOfWeek.SUNDAY) 0 else 7 - today.dayOfWeek.ordinal
    val currentWeekStartDate = today.minus(sundayOffset, DateTimeUnit.DAY)
    val targetDateForDayOfWeek = currentWeekStartDate.plus(
        when (dayOfWeek) {
            DayOfWeek.SUNDAY -> 0
            DayOfWeek.MONDAY -> 1
            DayOfWeek.TUESDAY -> 2
            DayOfWeek.WEDNESDAY -> 3
            DayOfWeek.THURSDAY -> 4
            DayOfWeek.FRIDAY -> 5
            DayOfWeek.SATURDAY -> 6
        }, 
        DateTimeUnit.DAY
    )
    
    return when {
        targetDateForDayOfWeek == today -> OiTheme.colors.textOnPrimary
        else -> OiTheme.colors.textPrimary
    }
}

@Preview
@Composable
private fun OiWeeklyCalendarPreview() {
    val currentDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date
    OiWeeklyCalendar(
        today = currentDate,
        selectedDate = currentDate.plus(1, DateTimeUnit.DAY)
    )
}