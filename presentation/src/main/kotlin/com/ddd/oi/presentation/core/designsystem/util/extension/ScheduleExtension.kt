package com.ddd.oi.presentation.core.designsystem.util.extension

import com.ddd.oi.domain.model.Schedule
import com.ddd.oi.presentation.core.designsystem.component.oicalendar.OiCalendarDefaults
import com.ddd.oi.presentation.schedule.model.UiCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun List<Schedule>.groupByDate(
    zoneId: ZoneId = OiCalendarDefaults.zone
): ImmutableMap<LocalDate, ImmutableList<Schedule>> {
    val map = mutableMapOf<LocalDate, MutableList<Schedule>>()

    for (schedule in this) {
        val startDate = Instant.ofEpochMilli(schedule.startedAt)
            .atZone(zoneId).toLocalDate()
        val endDate = Instant.ofEpochMilli(schedule.endedAt)
            .atZone(zoneId).toLocalDate()

        var date = startDate
        while (!date.isAfter(endDate)) {
            map.getOrPut(date) { mutableListOf() }.add(schedule)
            date = date.plusDays(1)
        }
    }

    return map.mapValues { it.value.toImmutableList() }.toImmutableMap()
}

fun ImmutableMap<LocalDate, ImmutableList<Schedule>>.groupCategoriesByDate(): ImmutableMap<LocalDate, ImmutableList<UiCategory>> {
    return this.mapValues { (_, schedules) ->
        schedules.map { UiCategory.from(it.category) }.toImmutableList()
    }.toImmutableMap()
}
