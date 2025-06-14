package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

internal abstract class OiCalendarModel() {
    abstract val today: OiCalendarDate

    abstract val firstDayOfWeek: Int

    abstract val weekdayNames: List<String>

    abstract fun getMonth(date: OiCalendarDate): OiCalendarMonth

    abstract fun getMonth(year: Int, month: Int): OiCalendarMonth
}

internal class OiCalendarModelImpl(locale: Locale) : OiCalendarModel() {

    override val today
        get(): OiCalendarDate {
            val systemLocalDate = LocalDate.now()
            return OiCalendarDate(
                year = systemLocalDate.year,
                month = systemLocalDate.monthValue,
                dayOfMonth = systemLocalDate.dayOfMonth,
                utcTimeMillis =
                    systemLocalDate
                        .atTime(LocalTime.MIDNIGHT)
                        .atZone(OiCalendarDefaults.zone)
                        .toInstant()
                        .toEpochMilli()
            )
        }

    override val firstDayOfWeek: Int = WeekFields.of(locale).firstDayOfWeek.value

    override val weekdayNames: List<String> =
        with(locale) {
            DayOfWeek.entries.map {
                it.getDisplayName(TextStyle.NARROW, this)
            }
        }

    override fun getMonth(date: OiCalendarDate): OiCalendarMonth {
        return getMonth(LocalDate.of(date.year, date.month, 1))
    }

    override fun getMonth(year: Int, month: Int): OiCalendarMonth {
        return getMonth(LocalDate.of(year, month, 1))
    }

    private fun getMonth(firstDayLocalDate: LocalDate): OiCalendarMonth {
        val difference = firstDayLocalDate.dayOfWeek.value - firstDayOfWeek
        val daysFromStartOfWeekToFirstOfMonth =
            if (difference < 0) {
                difference + DaysInWeek
            } else {
                difference
            }
        val previousMonth = firstDayLocalDate.minusMonths(1)
        val previousMonthDays = previousMonth.lengthOfMonth()

        val firstDayEpochMillis =
            firstDayLocalDate
                .atTime(LocalTime.MIDNIGHT)
                .atZone(OiCalendarDefaults.zone)
                .toInstant()
                .toEpochMilli()
        return OiCalendarMonth(
            year = firstDayLocalDate.year,
            month = firstDayLocalDate.monthValue,
            numberOfDays = firstDayLocalDate.lengthOfMonth(),
            daysFromStartOfWeekToFirstOfMonth = daysFromStartOfWeekToFirstOfMonth,
            startUtcTimeMillis = firstDayEpochMillis,
            previousMonthDays = previousMonthDays
        )
    }
}

internal data class OiCalendarDate(
    val year: Int,
    val month: Int,
    val dayOfMonth: Int,
    val utcTimeMillis: Long
) : Comparable<OiCalendarDate> {
    override fun compareTo(other: OiCalendarDate): Int =
        this.utcTimeMillis.compareTo(other.utcTimeMillis)
}

internal data class OiCalendarMonth(
    val year: Int,
    val month: Int,
    val numberOfDays: Int,
    val daysFromStartOfWeekToFirstOfMonth: Int,
    val startUtcTimeMillis: Long,
    val previousMonthDays: Int,
) {
    val totalCells: Int
        get() {
            val totalCells = daysFromStartOfWeekToFirstOfMonth + numberOfDays
            return (totalCells + DaysInWeek - 1) / DaysInWeek
        }

    fun getLocalDateForDayOfMonth(dayOfMonth: Int): LocalDate {
        val utcMillis = getDayStartUtcMillis(dayOfMonth)
        return Instant.ofEpochMilli(utcMillis)
            .atZone(OiCalendarDefaults.zone)
            .toLocalDate()
    }

    private fun getDayStartUtcMillis(dayOfMonth: Int): Long {
        return startUtcTimeMillis + (dayOfMonth - 1) * MillisecondsIn24Hours
    }
}