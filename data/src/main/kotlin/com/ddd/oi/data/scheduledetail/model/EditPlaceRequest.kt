package com.ddd.oi.data.scheduledetail.model

import kotlinx.serialization.Serializable

@Serializable
data class EditPlaceRequest(
    val startTime: String? = null,
    val targetDate: String,
    val memo: String,
    val spotName: String,
    val latitude: Double,
    val longitude: Double
)