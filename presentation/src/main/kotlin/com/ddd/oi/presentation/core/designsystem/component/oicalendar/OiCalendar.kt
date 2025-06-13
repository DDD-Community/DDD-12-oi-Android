package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OiCalendar(
    year: Int,
    month: Int,
    selectedDateMillis: Long,
    onDateSelectionChange: (dateInMillis: Long) -> Unit,
    colors: OiDatePickerColors = OiDatePickerDefaults.colors()
) {
    val locale = getCurrentLocale()
    val oiCalendarModel = remember { OiCalendarModelImpl(locale) }
    DateContent(
        year = year,
        month = month,
        selectedDateMillis = selectedDateMillis,
        onDateSelectionChange = onDateSelectionChange,
        oiCalendarModel = oiCalendarModel,
        colors = colors
    )
}

@ExperimentalMaterial3Api
@Composable
internal fun DateContent(
    year: Int,
    month: Int,
    selectedDateMillis: Long?,
    onDateSelectionChange: (dateInMillis: Long) -> Unit,
    oiCalendarModel: OiCalendarModel,
    colors: OiDatePickerColors,
) {
    val today = oiCalendarModel.today
    Column {
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
            )
        )
    }
}

@Composable
internal fun OiWeekDays(
    colors: OiDatePickerColors,
    oiCalendarModel: OiCalendarModel
) {
    val firstDayOfWeek = oiCalendarModel.firstDayOfWeek
    val weekdays = oiCalendarModel.weekdayNames
    val dayNames = arrayListOf<Pair<String, String>>()
    for (i in firstDayOfWeek - 1 until weekdays.size) {
        dayNames.add(weekdays[i])
    }
    for (i in 0 until firstDayOfWeek - 1) {
        dayNames.add(weekdays[i])
    }
    Row(
        modifier =
            Modifier
                .height(34.dp)
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        dayNames.fastForEach {
            Box(
                modifier =
                    Modifier
                        .clearAndSetSemantics { contentDescription = it.first }
                        .size(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = it.second,
                    modifier = Modifier.wrapContentSize(),
                    textAlign = TextAlign.Center,
                    color = colors.dayContentColor,
                    style = OiTheme.typography.bodyMediumBold
                )
            }
        }
    }
}


@Composable
internal fun OiMonth(
    month: OiCalendarMonth,
    onDateSelectionChange: (dateInMillis: Long) -> Unit,
    startDateMillis: Long?,
    todayMillis: Long,
    colors: OiDatePickerColors,
    modifier: Modifier = Modifier,
) {
    var cellIndex = 0

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        for (weekIndex in 0 until month.totalCells) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(34.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                for (dayIndex in 0 until DaysInWeek) {
                    if (cellIndex < month.daysFromStartOfWeekToFirstOfMonth) {
                        val leadingOffset = month.daysFromStartOfWeekToFirstOfMonth - cellIndex
                        val dayNumber = month.previousMonthDays - leadingOffset + 1
                        OiDay(
                            selected = false,
                            onClick = {},
                            dayNumber = dayNumber.toString(),
                            animateChecked = false,
                            enabled = false,
                            today = false,
                            modifier = Modifier,
                            colors = colors
                        )
                    } else if (cellIndex >= (month.daysFromStartOfWeekToFirstOfMonth + month.numberOfDays)) {
                        val trailingOffset =
                            cellIndex - (month.daysFromStartOfWeekToFirstOfMonth + month.numberOfDays)
                        val dayNumber = trailingOffset + 1
                        OiDay(
                            selected = false,
                            onClick = {},
                            dayNumber = dayNumber.toString(),
                            animateChecked = false,
                            enabled = false,
                            today = false,
                            modifier = Modifier,
                            colors = colors
                        )
                    } else {
                        val dayNumber = cellIndex - month.daysFromStartOfWeekToFirstOfMonth
                        val dateInMillis =
                            month.startUtcTimeMillis + (dayNumber * MillisecondsIn24Hours)
                        val isToday = dateInMillis == todayMillis
                        val todaySelected = dateInMillis == startDateMillis
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            OiDay(
                                selected = todaySelected,
                                dayNumber = (dayNumber + 1).toString(),
                                onClick = { onDateSelectionChange(dateInMillis) },
                                animateChecked = todaySelected,
                                enabled = true,
                                today = isToday,
                                modifier = Modifier,
                                colors = colors
                            )
                        }
                    }
                    cellIndex++
                }
            }
        }
    }
}

@Composable
fun OiDay(
    modifier: Modifier,
    dayNumber: String,
    selected: Boolean,
    onClick: () -> Unit = {},
    animateChecked: Boolean,
    enabled: Boolean,
    today: Boolean,
    colors: OiDatePickerColors,
) {
    Column(modifier = modifier.width(28.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            selected = selected,
            modifier = Modifier.requiredSize(28.dp),
            enabled = enabled,
            onClick = onClick,
            shape = CircleShape,
            color = colors
                .dayContainerColor(
                    today = today,
                    selected = selected,
                    enabled = enabled,
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
            modifier = Modifier.padding(top = 2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {

        }
    }
}

@Composable
internal fun getTextStyle(
    today: Boolean,
    selected: Boolean
): TextStyle = when {
    selected -> OiTheme.typography.bodyMediumRegular
    today -> OiTheme.typography.bodyMediumRegular
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
        OiCalendar(
            year = 2025,
            month = 6,
            selectedDateMillis = 0L,
            onDateSelectionChange = {}
        )
    }
}