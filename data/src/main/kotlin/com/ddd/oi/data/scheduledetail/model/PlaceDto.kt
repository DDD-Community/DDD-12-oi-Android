package com.ddd.oi.data.scheduledetail.model

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDto(
    val targetDate: String,
    val details: List<PlaceInfo>
)

@Serializable
data class PlaceInfo(
    val id: Long,
    val startTime: String? = null,
    val targetDate: String,
    val spotName: String,
    val latitude: Double,
    val longitude: Double,
    val memo: String,
    val category: String,
)
