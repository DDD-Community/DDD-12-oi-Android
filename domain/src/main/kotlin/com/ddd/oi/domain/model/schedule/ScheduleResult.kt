package com.ddd.oi.domain.model.schedule

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus

data class ScheduleResult(
    val schedules: Map<LocalDate, List<Schedule>>,
    val availableCategory: Set<Category>
)

fun List<Schedule>.groupByDate(
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): Map<LocalDate, List<Schedule>> {
    val map = mutableMapOf<LocalDate, MutableList<Schedule>>()

    for (schedule in this) {
        var date = schedule.startedAt
        while (date <= schedule.endedAt) {
            map.getOrPut(date) { mutableListOf() }.add(schedule)
            date = date.plus(1, DateTimeUnit.DAY)
        }
    }
    return map
        .mapValues { (_, list) -> list.sortedBy { it.createdAt } }
}