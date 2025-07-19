package com.ddd.oi.data.scheduledetail.model

import kotlinx.serialization.Serializable

@Serializable
data class ScheduleDetailRequest(
    val startTime: String,
    val targetDate: String,
    val memo: String,
    val spotName: String,
    val latitude: Double,
    val longitude: Double
)