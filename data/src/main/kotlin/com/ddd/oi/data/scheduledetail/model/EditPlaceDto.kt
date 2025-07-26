package com.ddd.oi.data.scheduledetail.model

import com.ddd.oi.domain.model.schedule.SchedulePlace
import kotlinx.serialization.Serializable

@Serializable
data class EditPlaceDto(
    val scheduleDetailId: Long,
    val startTime: String? = null,
    val targetDate: String,
    val memo: String,
    val spotName: String,
    val latitude: Double,
    val longitude: Double,
    val category: String
) {
    fun toDomain(): SchedulePlace {
        return SchedulePlace(
            id = scheduleDetailId,
            startTime = startTime,
            targetDate = targetDate,
            memo = memo,
            spotName = spotName,
            latitude = latitude,
            longitude = longitude,
            category = category
        )
    }
}

