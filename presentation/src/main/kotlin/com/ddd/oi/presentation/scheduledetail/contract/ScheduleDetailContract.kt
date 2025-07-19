package com.ddd.oi.presentation.scheduledetail.contract

import com.ddd.oi.domain.model.schedule.Place
import com.ddd.oi.domain.model.schedule.Schedule
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus


data class ScheduleDetailState(
    val schedule: Schedule,
    val placeUiState: PlaceUiState = PlaceUiState.Loading
) {

    val isMoreDateVisible: Boolean = schedule.startedAt.daysUntil(schedule.endedAt) > 1

    val scheduleDays: ImmutableList<ScheduleDay> = run {
        val placesByDate = (placeUiState as? PlaceUiState.Success)?.places ?: emptyMap()
        val days = schedule.startedAt.daysUntil(schedule.endedAt) + 1

        List(days) { dayIndex ->
            val currentDate = schedule.startedAt.plus(dayIndex, DateTimeUnit.DAY)
            ScheduleDay(
                day = dayIndex + 1,
                date = currentDate,
                places = placesByDate[currentDate.toString()]?.sortedWith(
                    compareBy(
                    { it.startTime == null },
                    { it.startTime }
                ))?.toImmutableList() ?: persistentListOf()
            )
        }.toPersistentList()
    }

    val dateList: ImmutableList<LocalDate>
        get() {
            val list = mutableListOf<LocalDate>()
            var current = schedule.startedAt
            while (current <= schedule.endedAt) {
                list.add(current)
                current = current.plus(1, DateTimeUnit.DAY)
            }
            return list.toImmutableList()
        }

    fun placesForDate(date: LocalDate): ImmutableList<Place> {
        return when (placeUiState) {
            is PlaceUiState.Success -> placeUiState.places[date.toString()]?.toImmutableList()
                ?: persistentListOf()

            else -> persistentListOf()
        }
    }
}

data class ScheduleDay(
    val day: Int,
    val date: LocalDate,
    val places: ImmutableList<Place>
)

sealed interface PlaceUiState {
    data object Loading : PlaceUiState

    data class Success(val places: Map<String, List<Place>>) : PlaceUiState

    data class Error(val message: String? = null) : PlaceUiState
}
