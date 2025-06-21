package com.ddd.oi.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val id: Long,
    val createdAt: Long,
    val updatedAt: Long,
    val title: String,
    val category: Category,
    val startedAt: Long,
    val endedAt: Long,
    val transportation: Transportation,
    val partySet: Set<Party>,
    val placeList: List<Place>
)

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