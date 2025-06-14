package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.ddd.oi.domain.model.Schedule
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.OiCalendarDimens
import com.ddd.oi.presentation.core.designsystem.util.getColor
import com.ddd.oi.presentation.core.designsystem.util.groupByDate
import com.ddd.oi.presentation.core.designsystem.util.groupCategoriesByDate
import com.ddd.oi.presentation.schedule.model.UiCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate
import java.util.Locale

@Composable
fun OiCalendar(
    year: Int,
    month: Int,
    selectedDateMillis: Long?,
    onDateSelectionChange: (dateInMillis: Long) -> Unit,
    colors: OiCalendarColors = OiCalendarDefaults.colors(),
    schedules: ImmutableMap<LocalDate, ImmutableList<UiCategory>>
) {
    val locale = getCurrentLocale()
    val oiCalendarModel = remember { OiCalendarModelImpl(locale) }
    DateContent(
        year = year,
        month = month,
        selectedDateMillis = selectedDateMillis,
        onDateSelectionChange = onDateSelectionChange,
        oiCalendarModel = oiCalendarModel,
        colors = colors,
        schedules = schedules
    )
}

@Composable
internal fun DateContent(
    year: Int,
    month: Int,
    selectedDateMillis: Long?,
    onDateSelectionChange: (dateInMillis: Long) -> Unit,
    oiCalendarModel: OiCalendarModel,
    colors: OiCalendarColors,
    schedules: ImmutableMap<LocalDate, ImmutableList<UiCategory>>
) {
    val today = oiCalendarModel.today
    Column(
        modifier = Modifier
            .padding(horizontal = Dimens.paddingMedium)
            .background(colors.containerColor)
    ) {
        OiWeekDays(colors = colors, oiCalendarModel = oiCalendarModel)
        OiMonth(
            month = oiCalendarModel.getMonth(year, month),
            onDateSelectionChange = onDateSelectionChange,
            startDateMillis = selectedDateMillis,
            todayMillis = today.utcTimeMillis,
            colors = colors,
            modifier = Modifier.padding(
                top = Dimens.paddingMediumSmall,
                bottom = Dimens.paddingMedium
            ),
            schedules = schedules
        )
    }
}

@Composable
internal fun OiWeekDays(
    colors: OiCalendarColors,
    oiCalendarModel: OiCalendarModel
) {
    val firstDayOfWeek = oiCalendarModel.firstDayOfWeek
    val weekdays = oiCalendarModel.weekdayNames
    val dayNames = remember(firstDayOfWeek, weekdays) {
        val names = arrayListOf<String>()
        for (i in firstDayOfWeek - 1 until weekdays.size) {
            names.add(weekdays[i])
        }
        for (i in 0 until firstDayOfWeek - 1) {
            names.add(weekdays[i])
        }
        names
    }
    Row(
        modifier =
            Modifier
                .height(OiCalendarDimens.calendarCellHeight)
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        dayNames.fastForEach {
            Box(
                modifier = Modifier.size(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it,
                    color = colors.weekdayContentColor,
                    style = OiTheme.typography.bodyMediumSemibold
                )
            }
        }
    }
}


@Composable
internal fun OiMonth(
    modifier: Modifier = Modifier,
    month: OiCalendarMonth,
    onDateSelectionChange: (dateInMillis: Long) -> Unit,
    startDateMillis: Long?,
    todayMillis: Long,
    colors: OiCalendarColors,
    schedules: ImmutableMap<LocalDate, ImmutableList<UiCategory>>
) {
    var cellIndex = 0

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Dimens.paddingMediumSmall)
    ) {
        for (weekIndex in 0 until month.totalCells) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(OiCalendarDimens.calendarCellHeight),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                for (dayIndex in 0 until DaysInWeek) {
                    if (cellIndex < month.daysFromStartOfWeekToFirstOfMonth) {
                        val leadingOffset = month.daysFromStartOfWeekToFirstOfMonth - cellIndex
                        val dayNumber = month.previousMonthDays - leadingOffset + 1
                        val localDate = month.getLocalDateForDayOfMonth(-leadingOffset + 1)
                        OiDay(
                            dayNumber = dayNumber.toString(),
                            colors = colors,
                            categories = schedules[localDate] ?: persistentListOf()
                        )
                    } else if (cellIndex >= (month.daysFromStartOfWeekToFirstOfMonth + month.numberOfDays)) {
                        val trailingOffset =
                            cellIndex - (month.daysFromStartOfWeekToFirstOfMonth + month.numberOfDays)
                        val dayNumber = trailingOffset + 1
                        val localDate =
                            month.getLocalDateForDayOfMonth(month.numberOfDays + dayNumber)
                        OiDay(
                            dayNumber = dayNumber.toString(),
                            colors = colors,
                            categories = schedules[localDate] ?: persistentListOf()
                        )
                    } else {
                        val dayNumber = cellIndex - month.daysFromStartOfWeekToFirstOfMonth
                        val dateInMillis =
                            month.startUtcTimeMillis + (dayNumber * MillisecondsIn24Hours)
                        val isToday = dateInMillis == todayMillis
                        val todaySelected = dateInMillis == startDateMillis
                        val localDate = month.getLocalDateForDayOfMonth(dayNumber)
                        OiDay(
                            selected = todaySelected,
                            dayNumber = (dayNumber + 1).toString(),
                            onClick = { onDateSelectionChange(dateInMillis) },
                            animateChecked = todaySelected,
                            enabled = true,
                            today = isToday,
                            colors = colors,
                            categories = schedules[localDate] ?: persistentListOf()
                        )
                    }
                    cellIndex++
                }
            }
        }
    }
}

@Composable
fun OiDay(
    modifier: Modifier = Modifier,
    dayNumber: String,
    selected: Boolean = false,
    onClick: () -> Unit = {},
    animateChecked: Boolean = false,
    enabled: Boolean = false,
    today: Boolean = false,
    colors: OiCalendarColors,
    categories: ImmutableList<UiCategory> = persistentListOf()
) {
    Column(
        modifier = modifier.width(OiCalendarDimens.dayWidth),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            selected = selected,
            modifier = Modifier.size(OiCalendarDimens.dayWidth),
            enabled = enabled,
            onClick = onClick,
            shape = CircleShape,
            color = colors
                .dayContainerColor(
                    today = today,
                    selected = selected,
                    animate = animateChecked
                )
                .value,
            contentColor =
                colors
                    .dayContentColor(
                        isToday = today,
                        selected = selected,
                        enabled = enabled,
                    )
                    .value,
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dayNumber,
                    modifier = Modifier,
                    style = getTextStyle(today, selected),
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            modifier = Modifier.padding(top = OiCalendarDimens.categoryPadding),
            horizontalArrangement = Arrangement.spacedBy(OiCalendarDimens.categoryPadding)
        ) {
            categories.forEach { category ->
                Box(
                    modifier = Modifier
                        .size(OiCalendarDimens.categoryCircleSize)
                        .background(color = category.getColor(), shape = CircleShape),
                )
            }
        }
    }
}

@Composable
internal fun getTextStyle(
    today: Boolean,
    selected: Boolean
): TextStyle = when {
    selected -> OiTheme.typography.bodyMediumSemibold
    today -> OiTheme.typography.bodyMediumMedium
    else -> OiTheme.typography.bodyMediumRegular
}

internal const val DaysInWeek: Int = 7
internal const val MillisecondsIn24Hours = 86400000L

@Composable
@ReadOnlyComposable
fun getCurrentLocale(): Locale {
    return LocalConfiguration.current.locales[0]
}

@Composable
@Preview(showBackground = true)
private fun OiCalendarPreview() {
    OiTheme {
        val mockData: List<Schedule> = MockOiCalendarScheduleData.generateMockSchedules()
        var selectedDateMillis by remember { mutableLongStateOf(0L) }
        Column(modifier = Modifier.fillMaxWidth()) {
            OiCalendar(
                year = 2025,
                month = 6,
                selectedDateMillis = selectedDateMillis,
                onDateSelectionChange = { selectedDateMillis = it },
                schedules = mockData.groupByDate().groupCategoriesByDate()
            )
        }
    }
}

