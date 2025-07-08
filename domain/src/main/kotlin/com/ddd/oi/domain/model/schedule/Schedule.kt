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

// todo api 응답이랑 매핑 되게 수정 필요
@Serializable
data class Place(
    val id: Long,
    val title: String,
    val memo: String,
    val startedAt: Long,
    val endedAt: Long,
    val latLng: LatLng
)

@Serializable
data class LatLng(
    val latitude: Double,
    val longitude: Double
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