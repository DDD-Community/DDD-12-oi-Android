package com.ddd.oi.presentation.core.designsystem.component.oidaterangepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.ddd.oi.domain.model.schedule.Category
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.OiCalendarColors
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.OiCalendarDefaults
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.OiCalendarModel
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.OiCalendarMonth
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.OiWeekDays
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.getCurrentLocale
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.DaysInWeek
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.OiMonth
import com.ddd.oi.presentation.core.designsystem.theme.OiTheme
import com.ddd.oi.presentation.core.designsystem.util.Dimens
import com.ddd.oi.presentation.core.designsystem.util.OiCalendarDimens
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Composable
fun OiDateRangePicker(
    displayedMonth: LocalDate,
    selectedStartDate: LocalDate? = null,
    selectedEndDate: LocalDate? = null,
    schedules: ImmutableMap<LocalDate, ImmutableList<Category>>,
    firstBlockedDateAfterStart: LocalDate? = null,
    onDatesSelectionChange: (startDate: LocalDate?, endDate: LocalDate?) -> Unit,
    onBlockedDateClicked: () -> Unit = {}
) {
    val locale = getCurrentLocale()
    val oiCalendarModel = remember { OiCalendarModel(locale) }

    OiDateRangePickerContent(
        oiCalendarModel = oiCalendarModel,
        displayedMonth = displayedMonth,
        selectedStartDate = selectedStartDate,
        selectedEndDate = selectedEndDate,
        schedules = schedules,
        firstBlockedDateAfterStart = firstBlockedDateAfterStart,
        onDatesSelectionChange = onDatesSelectionChange,
        onBlockedDateClicked = onBlockedDateClicked
    )
}

@Composable
private fun OiDateRangePickerContent(
    oiCalendarModel: OiCalendarModel,
    displayedMonth: LocalDate,
    selectedStartDate: LocalDate? = null,
    selectedEndDate: LocalDate? = null,
    schedules: ImmutableMap<LocalDate, ImmutableList<Category>>,
    firstBlockedDateAfterStart: LocalDate? = null,
    onDatesSelectionChange: (startDate: LocalDate?, endDate: LocalDate?) -> Unit,
    onBlockedDateClicked: () -> Unit = {},
    colors: OiCalendarColors = OiCalendarDefaults.colors(isRangeMode = true)
) {
    val monthKey = displayedMonth.year * 100 + displayedMonth.monthNumber
    val calendarMonth = remember(monthKey, selectedStartDate, selectedEndDate, schedules) {
        oiCalendarModel.getMonthForRange(
            displayedMonth = displayedMonth,
            categories = schedules,
            selectedStartDate = selectedStartDate,
            selectedEndDate = selectedEndDate,
            firstBlockedDateAfterStart = firstBlockedDateAfterStart
        )
    }

    val onDateSelectionChange = { dateInMillis: LocalDate ->
        updateDateSelection(
            selectedDate = dateInMillis,
            currentStartDate = selectedStartDate,
            currentEndDate = selectedEndDate,
            firstBlockedDateAfterStart = firstBlockedDateAfterStart,
            onDatesSelectionChange = onDatesSelectionChange,
            onBlockedDateClicked = onBlockedDateClicked
        )
    }

    val rangeSelectionInfo: OiSelectedRangeInfo? =
        if (selectedStartDate != null && selectedEndDate != null) {
            remember(selectedStartDate, selectedEndDate, calendarMonth) {
                OiSelectedRangeInfo.calculateRangeInfo(
                    month = calendarMonth,
                    startDate = selectedStartDate,
                    endDate = selectedEndDate
                )
            }
        } else {
            null
        }

    Column(
        modifier = Modifier
            .background(colors.containerColor)
            .padding(horizontal = Dimens.paddingMedium)
    ) {
        OiWeekDays(colors = colors, oiCalendarModel = oiCalendarModel)
        OiMonth(
            month = calendarMonth,
            onDateSelectionChange = onDateSelectionChange,
            colors = colors,
            rangeSelectionInfo = rangeSelectionInfo,
            modifier = Modifier.padding(
                top = Dimens.paddingMediumSmall,
                bottom = Dimens.paddingMedium
            ),
        )
    }
}


internal fun ContentDrawScope.drawRangeBackground(
    selectedRangeInfo: OiSelectedRangeInfo,
    color: Color
) {
    val circleSize = OiCalendarDimens.dayWidth.toPx()
    val circleRadius = circleSize / 2f
    val itemContainerHeight = OiCalendarDimens.calendarCellHeight.toPx()

    val verticalOffset = 0f
    val verticalSpacing = Dimens.paddingMediumSmall.toPx()

    val (x1, y1) = selectedRangeInfo.gridStartCoordinates
    val (x2, y2) = selectedRangeInfo.gridEndCoordinates


    fun getItemCenterX(dayIndex: Int): Float {
        val availableSpace = this.size.width - circleSize
        return dayIndex * (availableSpace / (DaysInWeek - 1)) + circleSize / 2f
    }

    val startCenterX = getItemCenterX(x1)
    val endCenterX = getItemCenterX(x2)

    val startX = if (selectedRangeInfo.firstIsSelectionStart) {
        startCenterX - circleRadius
    } else {
        0f
    }
    val endX = if (selectedRangeInfo.lastIsSelectionEnd) {
        endCenterX + circleRadius
    } else {
        this.size.width
    }

    val startY = y1 * (itemContainerHeight + verticalSpacing) + verticalOffset
    val endY = y2 * (itemContainerHeight + verticalSpacing) + verticalOffset

    drawRoundRect(
        color = color,
        topLeft = Offset(startX, startY),
        size = Size(
            width = when {
                y1 == y2 -> endX - startX
                else -> this.size.width - startX
            },
            height = circleSize
        ),
        cornerRadius = CornerRadius(x = circleRadius, y = circleRadius)
    )

    if (y1 != y2) {
        for (weekOffset in 1 until (y2 - y1)) {
            drawRoundRect(
                color = color,
                topLeft = Offset(
                    0f,
                    startY + (weekOffset * (itemContainerHeight + verticalSpacing))
                ),
                size = Size(width = this.size.width, height = circleSize),
                cornerRadius = CornerRadius(x = circleRadius, y = circleRadius)
            )
        }

        drawRoundRect(
            color = color,
            topLeft = Offset(0f, endY),
            size = Size(
                width = endX,
                height = circleSize
            ),
            cornerRadius = CornerRadius(x = circleRadius, y = circleRadius)
        )
    }
}

internal class OiSelectedRangeInfo(
    val gridStartCoordinates: IntOffset,
    val gridEndCoordinates: IntOffset,
    val firstIsSelectionStart: Boolean,
    val lastIsSelectionEnd: Boolean
) {
    companion object {
        fun calculateRangeInfo(
            month: OiCalendarMonth,
            startDate: LocalDate,
            endDate: LocalDate
        ): OiSelectedRangeInfo? {
            val days = month.days

            val firstDateInGrid = days.first().date
            val lastDateInGrid = days.last().date

            if (startDate > lastDateInGrid || endDate < firstDateInGrid) return null

            val firstIsSelectionStart = startDate >= firstDateInGrid
            val lastIsSelectionEnd = endDate <= lastDateInGrid

            val startIndex = if (firstIsSelectionStart) {
                days.indexOfFirst { it.date == startDate && it.isCurrentMonth }
                    .takeIf { it != -1 } ?: days.indexOfFirst { it.date >= startDate }
            } else {
                days.indexOfFirst { it.isCurrentMonth }
            }

            val endIndex = if (lastIsSelectionEnd) {
                days.indexOfLast { it.date == endDate && it.isCurrentMonth }
                    .takeIf { it != -1 } ?: days.indexOfLast { it.date <= endDate }
            } else {
                days.indexOfLast { it.isCurrentMonth }
            }

            if (startIndex == -1 || endIndex == -1) return null

            return OiSelectedRangeInfo(
                gridStartCoordinates = IntOffset(startIndex % DaysInWeek, startIndex / DaysInWeek),
                gridEndCoordinates = IntOffset(endIndex % DaysInWeek, endIndex / DaysInWeek),
                firstIsSelectionStart = firstIsSelectionStart,
                lastIsSelectionEnd = lastIsSelectionEnd
            )
        }
    }
}

private fun updateDateSelection(
    selectedDate: LocalDate,
    currentStartDate: LocalDate?,
    currentEndDate: LocalDate?,
    firstBlockedDateAfterStart: LocalDate?,
    onDatesSelectionChange: (startDate: LocalDate?, endDate: LocalDate?) -> Unit,
    onBlockedDateClicked: () -> Unit
) {
    val isBlockedDate = firstBlockedDateAfterStart != null && selectedDate >= firstBlockedDateAfterStart
    
    when {
        // 블록된 날짜를 클릭한 경우
        isBlockedDate -> {
            when {
                // startDate만 있고 endDate가 없는 경우: 스낵바 표시
                currentStartDate != null && currentEndDate == null -> {
                    onBlockedDateClicked()
                }
                // startDate와 endDate가 모두 있는 경우: 클릭한 날로 startDate 초기화
                currentStartDate != null && currentEndDate != null -> {
                    onDatesSelectionChange(selectedDate, null)
                }
                // 아무것도 선택되지 않은 경우: 일반적인 시작일 설정
                else -> {
                    onDatesSelectionChange(selectedDate, null)
                }
            }
        }
        
        // 일반적인 날짜 선택 로직
        (currentStartDate == null && currentEndDate == null) ||
                (currentStartDate != null && currentEndDate != null) -> {
            onDatesSelectionChange(selectedDate, null)
        }

        currentStartDate != null && selectedDate >= currentStartDate -> {
            onDatesSelectionChange(currentStartDate, selectedDate)
        }

        else -> {
            onDatesSelectionChange(selectedDate, null)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OiDateRangePickerPreview() {
    OiTheme {
        var displayedMonth by remember { mutableStateOf(LocalDate(2025, 6, 1)) }
        var selectedStartDate by remember { mutableStateOf<LocalDate?>(null) }
        var selectedEndDate by remember { mutableStateOf<LocalDate?>(null) }
        Column(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                displayedMonth = displayedMonth.minus(1, DateTimeUnit.MONTH)
            }) {
                Text("이전달")
            }
            Button(onClick = {
                displayedMonth = displayedMonth.plus(1, DateTimeUnit.MONTH)
            }) {
                Text("다음달")
            }
            OiDateRangePicker(
                displayedMonth = displayedMonth,
                selectedStartDate = selectedStartDate,
                selectedEndDate = selectedEndDate,
                onDatesSelectionChange = { s, e ->
                    selectedStartDate = s
                    selectedEndDate = e
                },
                schedules = persistentMapOf()
            )
        }
    }
}