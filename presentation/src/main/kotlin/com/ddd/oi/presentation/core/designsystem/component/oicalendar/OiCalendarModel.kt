package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import androidx.compose.runtime.Immutable
import com.ddd.oi.domain.model.Category
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import java.time.format.TextStyle
import java.util.Locale


internal data class OiCalendarModel(val locale: Locale) {

    companion object {
        private val TODAY = Clock.System.todayIn(TimeZone.currentSystemDefault())
        private val FIRST_DAY_OF_WEEK = DayOfWeek.SUNDAY.value
    }

    private val allWeekdayNames: List<String> = DayOfWeek.entries.map {
        it.getDisplayName(TextStyle.NARROW, locale)
    }

    val orderedWeekdayNames: List<String> = run {
        val startIndex = FIRST_DAY_OF_WEEK - 1
        allWeekdayNames.drop(startIndex) + allWeekdayNames.take(startIndex)
    }

    fun getMonth(
        selectedDate: LocalDate,
        categories: ImmutableMap<LocalDate, ImmutableList<Category>>
    ): OiCalendarMonth {
        val firstDayOfMonth =
            LocalDate(selectedDate.year, selectedDate.month, 1)
        val offset = ((firstDayOfMonth.dayOfWeek.isoDayNumber - FIRST_DAY_OF_WEEK + 7) % 7)

        return OiCalendarMonth(createOiDays(firstDayOfMonth, selectedDate, offset, categories))
    }

    private fun createOiDays(
        currentMonth: LocalDate,
        selectedDate: LocalDate,
        offset: Int,
        schedules: ImmutableMap<LocalDate, ImmutableList<Category>>,
    ): ImmutableList<OiCalendarDay> {
        val daysInMonth = currentMonthLength(currentMonth)
        val totalCells = ((offset + daysInMonth + 6) / 7) * 7
        val startDate = currentMonth.minus(offset, DateTimeUnit.DAY)
        return List(totalCells) { i ->
            val date = startDate.plus(i, DateTimeUnit.DAY)
            OiCalendarDay(
                date = date,
                isCurrentMonth = date.monthNumber == currentMonth.monthNumber,
                isToday = date == TODAY,
                animateChecked = date == selectedDate,
                isSelected = date == selectedDate,
                categories = schedules[date] ?: persistentListOf()
            )
        }.toImmutableList()
    }

    private fun currentMonthLength(date: LocalDate): Int {
        val nextMonth = if (date.monthNumber == 12)
            LocalDate(date.year + 1, 1, 1)
        else
            LocalDate(date.year, date.monthNumber + 1, 1)
        return nextMonth.minus(1, DateTimeUnit.DAY).dayOfMonth
    }
}

@Immutable
internal data class OiCalendarMonth(
    val days: ImmutableList<OiCalendarDay>
) {
    val totalWeek: Int
        get() = (days.size + DaysInWeek - 1) / DaysInWeek
}

@Immutable
internal data class OiCalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isToday: Boolean,
    val animateChecked: Boolean,
    val isSelected: Boolean,
    val categories: ImmutableList<Category>
) {
    val dayNumber: Int = date.dayOfMonth
}

