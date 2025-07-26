package com.ddd.oi.data.scheduledetail.mapper

import com.ddd.oi.data.scheduledetail.model.EditPlaceRequest
import com.ddd.oi.domain.model.schedule.SchedulePlace

internal fun SchedulePlace.toEditRequest(): EditPlaceRequest {
    return EditPlaceRequest(
        startTime = startTime,
        targetDate = targetDate,
        memo = memo,
        spotName = spotName,
        latitude = latitude,
        longitude = longitude,
    )
}