package com.ddd.oi.presentation.core.designsystem.component.mapper

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.toLocalDateTime
import java.time.format.TextStyle
import java.util.Locale


internal fun formatDateRange(startDate: LocalDate, endDate: LocalDate, timeZone: TimeZone = TimeZone.currentSystemDefault()): String {
    val yearShort = startDate.year % 100
    val start = "%02d.%02d.%02d".format(yearShort, startDate.monthNumber, startDate.dayOfMonth)
    val end = "%02d.%02d".format(endDate.monthNumber, endDate.dayOfMonth)

    return "$start - $end"
}

internal fun formatToScheduleHeaderDate(date: LocalDate, locale: Locale): String {
    val month = date.monthNumber
    val day = date.dayOfMonth

    val dayOfWeek = DayOfWeek.of(date.dayOfWeek.isoDayNumber)
    val dayOfWeekName = dayOfWeek.getDisplayName(TextStyle.SHORT, locale)

    return "${month}월 ${day}일 ($dayOfWeekName)"
}