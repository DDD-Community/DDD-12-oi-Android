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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.util.fastForEach
import com.ddd.oi.domain.model.Category
import com.ddd.oi.presentation.core.designsystem.component.mapper.getColor
import com.ddd.oi.presentation.core.designsystem.component.oidaterangepicker.OiSelectedRangeInfo
import com.ddd.oi.presentation.core.designsystem.component.oidaterangepicker.drawRangeBackground
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.OiCalendarDimens
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.util.Locale

@Composable
fun OiCalendar(
    selectedLocalDate: LocalDate,
    onDateSelectionChange: (LocalDate) -> Unit,
    schedules: ImmutableMap<LocalDate, ImmutableList<Category>>
) {
    val locale = getCurrentLocale()
    val oiCalendarModel = remember { OiCalendarModel(locale) }

    OiDateContent(
        oiCalendarModel = oiCalendarModel,
        selectedLocalDate = selectedLocalDate,
        onDateSelectionChange = onDateSelectionChange,
        schedules = schedules
    )
}

@Composable
private fun OiDateContent(
    oiCalendarModel: OiCalendarModel,
    selectedLocalDate: LocalDate,
    colors: OiCalendarColors = OiCalendarDefaults.colors(),
    schedules: ImmutableMap<LocalDate, ImmutableList<Category>>,
    onDateSelectionChange: (LocalDate) -> Unit = {}
) {
    val monthKey = selectedLocalDate.year * 100 + selectedLocalDate.monthNumber
    val rawMonth = remember(monthKey, schedules) {
        oiCalendarModel.getMonth(selectedLocalDate, schedules)
    }
    val calendarMonth = remember(rawMonth, selectedLocalDate) {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        OiCalendarMonth(
            days = rawMonth.days.map {
                it.copy(
                    isSelected = it.date == selectedLocalDate,
                    isToday = it.date == today
                )
            }.toImmutableList()
        )
    }
    Box(modifier = Modifier.background(colors.containerColor)) {
        Column(
            modifier = Modifier
                .padding(horizontal = Dimens.paddingMedium)
                .background(colors.containerColor)
        ) {
            OiWeekDays(colors = colors, oiCalendarModel = oiCalendarModel)
            OiMonth(
                month = calendarMonth,
                onDateSelectionChange = onDateSelectionChange,
                colors = colors,
                modifier = Modifier.padding(
                    top = Dimens.paddingMediumSmall,
                    bottom = Dimens.paddingMedium
                ),
            )
        }
    }
}

@Composable
internal fun OiWeekDays(
    colors: OiCalendarColors,
    oiCalendarModel: OiCalendarModel
) {
    val dayNames = oiCalendarModel.orderedWeekdayNames
    Row(
        modifier =
            Modifier
                .height(OiCalendarDimens.calendarCellHeight)
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        dayNames.fastForEach {
            Box(
                modifier = Modifier.size(OiCalendarDimens.dayWidth),
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
    onDateSelectionChange: (LocalDate) -> Unit,
    colors: OiCalendarColors,
    rangeSelectionInfo: OiSelectedRangeInfo? = null,
) {
    val rangeSelectionDrawModifier =
        if (rangeSelectionInfo != null) {
            Modifier.drawWithContent {
                drawRangeBackground(rangeSelectionInfo, colors.rangeBackgroundColor)
                drawContent()
            }
        } else {
            Modifier
        }
    Column(
        modifier = modifier
            .then(rangeSelectionDrawModifier),
        verticalArrangement = Arrangement.spacedBy(Dimens.paddingMediumSmall)
    ) {
        for (weekIndex in 0 until month.totalWeek) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(OiCalendarDimens.calendarCellHeight),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                for (dayIndex in 0 until DaysInWeek) {
                    val index = weekIndex * DaysInWeek + dayIndex
                    val day = month.days[index]
                    OiDay(
                        oiDay = day,
                        onClick = { onDateSelectionChange(day.date) },
                        colors = colors
                    )
                }
            }
        }
    }
}

@Composable
internal fun OiDay(
    modifier: Modifier = Modifier,
    oiDay: OiCalendarDay,
    onClick: () -> Unit,
    colors: OiCalendarColors,
) {
    Column(
        modifier = modifier.width(OiCalendarDimens.dayWidth),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            selected = oiDay.isSelected,
            modifier = Modifier.size(OiCalendarDimens.dayWidth),
            enabled = oiDay.isCurrentMonth,
            onClick = onClick,
            shape = CircleShape,
            color = colors.dayContainerColor(
                isToday = oiDay.isToday,
                isSelected = oiDay.isSelected,
                isRange = oiDay.isRange,
                animate = oiDay.animateChecked
            ).value,
            contentColor = colors.dayContentColor(
                isToday = oiDay.isToday,
                isSelected = oiDay.isSelected,
                isRange = oiDay.isRange,
                enabled = oiDay.isCurrentMonth,
            ).value,
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = oiDay.dayNumber.toString(),
                    modifier = Modifier,
                    style = getTextStyle(oiDay.isToday, oiDay.isSelected),
                )
            }
        }
        Row(
            modifier = Modifier.padding(top = OiCalendarDimens.categoryPadding),
            horizontalArrangement = Arrangement.spacedBy(OiCalendarDimens.categoryPadding)
        ) {
            oiDay.categories.forEach { category ->
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

@Composable
@ReadOnlyComposable
internal fun getCurrentLocale(): Locale {
    return LocalConfiguration.current.locales[0]
}

@Composable
@Preview(showBackground = true)
private fun OiCalendarPreview() {
    OiTheme {
        var selectedDateMillis by remember { mutableStateOf<LocalDate>(LocalDate(2025, 6, 16)) }

        Column(modifier = Modifier.fillMaxWidth()) {
            OiCalendar(
                selectedLocalDate = selectedDateMillis,
                onDateSelectionChange = { selectedDateMillis = it },
                schedules = persistentMapOf()
            )
        }
    }
}

