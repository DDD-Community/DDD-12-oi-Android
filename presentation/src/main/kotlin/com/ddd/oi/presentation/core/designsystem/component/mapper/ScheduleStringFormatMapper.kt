package com.ddd.oi.presentation.core.designsystem.component.mapper

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.isoDayNumber
import java.time.format.TextStyle
import java.util.Locale


internal fun formatDateRange(
    startDate: LocalDate,
    endDate: LocalDate,
    isDetail: Boolean = false
): String {
    val yearShort = startDate.year % 100
    val start = "%02d.%02d.%02d".format(yearShort, startDate.monthNumber, startDate.dayOfMonth)
    if (startDate == endDate && !isDetail) return start
    val end =
        if (isDetail) "%02d.%02d.%02d".format(
            yearShort,
            endDate.monthNumber,
            endDate.dayOfMonth
        )
        else "%02d.%02d".format(endDate.monthNumber, endDate.dayOfMonth)

    return "$start - $end"
}

internal fun formatToScheduleDetailActiveDate(activeDate: LocalDate): String {
    return "%02d.%02d".format(activeDate.monthNumber, activeDate.dayOfMonth)
}

internal fun formatToScheduleHeaderDate(date: LocalDate, locale: Locale): String {
    val month = date.monthNumber
    val day = date.dayOfMonth

    val dayOfWeek = DayOfWeek.of(date.dayOfWeek.isoDayNumber)
    val dayOfWeekName = dayOfWeek.getDisplayName(TextStyle.SHORT, locale)

    return "${month}월 ${day}일 ($dayOfWeekName)"
}