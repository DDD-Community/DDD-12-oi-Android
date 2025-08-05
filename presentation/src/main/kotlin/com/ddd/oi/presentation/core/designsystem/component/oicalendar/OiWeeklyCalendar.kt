package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import java.time.format.TextStyle

@Composable
fun OiWeeklyCalendar(
    modifier: Modifier = Modifier,
    today: LocalDate,
    selectedDate: LocalDate,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        DayOfWeek.entries.run {
            drop(DayOfWeek.SUNDAY.value.dec()) + take(DayOfWeek.SUNDAY.value.dec())
        }.forEach {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        shape = CircleShape,
                        color = getBackgroundColor(
                            today = today,
                            selectedDate = selectedDate,
                            dayOfWeek = it,
                        )
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = it.getDisplayName(TextStyle.NARROW, getCurrentLocale()),
                    color = getTextColor(
                        today = today,
                        selectedDate = selectedDate,
                        dayOfWeek = it,
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
    return when (dayOfWeek) {
        selectedDate.dayOfWeek -> Color(0xFFEDEDED)
        today.dayOfWeek -> OiTheme.colors.backgroundPrimary
        else -> Color.Transparent
    }
}

@Composable
private fun getTextColor(
    today: LocalDate,
    selectedDate: LocalDate,
    dayOfWeek: DayOfWeek
): Color {
    return when (dayOfWeek) {
        today.dayOfWeek -> OiTheme.colors.textOnPrimary
        selectedDate.dayOfWeek -> OiTheme.colors.textPrimary
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