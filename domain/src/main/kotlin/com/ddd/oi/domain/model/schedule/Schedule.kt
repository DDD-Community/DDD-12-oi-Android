package com.ddd.oi.domain.model.schedule

import kotlinx.datetime.LocalDate

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val id: Long,
    val title: String,
    val category: Category,
    val startedAt: LocalDate,
    val endedAt: LocalDate,
    val transportation: Transportation,
    val partySet: Set<Party>,
    val placeList: List<Place>
)

@Serializable
data class Place(
    val id: Long,
    val startTime: String? = null,
    val targetDate: String,
    val memo: String,
    val spotName: String,
    val latitude: Double,
    val longitude: Double,
    val category: String,
)


@Serializable
enum class Category {
    Travel, Date, Daily, Business, Etc
}

@Serializable
enum class Transportation {
    Car, Public, Bicycle, Walk
}

@Serializable
enum class Party {
    Alone, Friend, OtherHalf, Parent, Sibling, Children, Pet, Etc
}

data class ScheduleDetail(
    val details: Map<String, List<Place>>
)