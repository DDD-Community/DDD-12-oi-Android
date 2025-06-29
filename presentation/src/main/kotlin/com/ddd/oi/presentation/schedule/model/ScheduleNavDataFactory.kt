package com.ddd.oi.presentation.schedule.model

import com.ddd.oi.domain.model.schedule.Schedule
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

object ScheduleNavDataFactory {
    fun createLocalDateCreate(localDate: LocalDate): ScheduleNavData {
        val date = localDate.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
        return ScheduleNavData(startedAt = date, endedAt = date)
    }

    fun createForCopy(original: Schedule): ScheduleNavData {
        return ScheduleNavData(
            title = original.title,
            category = original.category,
            transportation = original.transportation,
            party = original.partySet,
            placeList = original.placeList
        )
    }

    fun createForEdit(original: Schedule): ScheduleNavData {
        return ScheduleNavData(
            id = original.id,
            title = original.title,
            category = original.category,
            startedAt = original.startedAt.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
            endedAt = original.endedAt.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
            transportation = original.transportation,
            party = original.partySet,
            placeList = original.placeList
        )
    }
}