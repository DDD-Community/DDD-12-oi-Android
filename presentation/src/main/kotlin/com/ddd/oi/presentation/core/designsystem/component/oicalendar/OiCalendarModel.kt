package com.ddd.oi.presentation.core.designsystem.component.oicalendar

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

internal abstract class OiCalendarModel() {
    abstract val today: OiCalendarDate

    abstract val firstDayOfWeek: Int

    abstract val weekdayNames: List<Pair<String, String>>

    abstract fun getMonth(date: OiCalendarDate): OiCalendarMonth

    abstract fun getMonth(year: Int, /* @IntRange(from = 1, to = 12) */ month: Int): OiCalendarMonth
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
                        .atZone(utcTimeZoneId)
                        .toInstant()
                        .toEpochMilli()
            )
        }

    override val firstDayOfWeek: Int = WeekFields.of(locale).firstDayOfWeek.value

    override val weekdayNames: List<Pair<String, String>> =
        with(locale) {
            DayOfWeek.entries.map {
                it.getDisplayName(TextStyle.FULL, this) to
                        it.getDisplayName(TextStyle.NARROW, this)
            }
        }

    private val utcTimeZoneId: ZoneId = ZoneId.of("UTC")

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
                .atZone(utcTimeZoneId)
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
    val endUtcTimeMillis: Long = startUtcTimeMillis + (numberOfDays * MillisecondsIn24Hours) - 1

    val totalCells: Int
        get() {
            val totalCells = daysFromStartOfWeekToFirstOfMonth + numberOfDays
            return (totalCells + DaysInWeek - 1) / DaysInWeek
        }

    fun getDayStartUtcMillis(dayOfMonth: Int): Long {
        return startUtcTimeMillis + (dayOfMonth - 1) * MillisecondsIn24Hours
    }

    fun getDayEndUtcMillis(dayOfMonth: Int): Long {
        return startUtcTimeMillis + (dayOfMonth * MillisecondsIn24Hours) - 1
    }
}