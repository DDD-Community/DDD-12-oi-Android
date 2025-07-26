package com.ddd.oi.presentation.scheduledetail.contract

import com.ddd.oi.domain.model.schedule.SchedulePlace
import com.ddd.oi.domain.model.schedule.Schedule
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
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
                        { it.startTime },
                        { it.startTime == null },
                    ))?.toImmutableList() ?: persistentListOf()
            )
        }.toPersistentList()
    }

    val lazyColumnList: PersistentList<SheetListItem>
        get() {
            return buildList<SheetListItem> {
                scheduleDays.forEach { day ->
                    add(SheetListItem.Header(day.day, day.date))
                    if (day.places.isNotEmpty()) {
                        day.places.forEachIndexed { index, place ->
                            add(
                                SheetListItem.PlaceItem(
                                    place,
                                    day.date,
                                    index + 1
                                )
                            )
                        }
                    } else {
                        add(SheetListItem.EmptyPlace(day.date))
                    }
                }
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

    fun placesForDate(date: LocalDate): ImmutableList<SchedulePlace> {
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
    val places: ImmutableList<SchedulePlace>
)

sealed interface PlaceUiState {
    data object Loading : PlaceUiState

    data class Success(val places: Map<String, List<SchedulePlace>>) : PlaceUiState

    data class Error(val message: String? = null) : PlaceUiState
}

sealed interface SheetListItem {
    val date: LocalDate

    data class Header(val day: Int, override val date: LocalDate) : SheetListItem
    data class PlaceItem(val place: SchedulePlace, override val date: LocalDate, val index: Int) :
        SheetListItem

    data class EmptyPlace(override val date: LocalDate) : SheetListItem
}
