package com.ddd.oi.data.scheduledetail.mapper

import com.ddd.oi.data.scheduledetail.model.PlaceInfo
import com.ddd.oi.domain.model.schedule.Place

internal fun PlaceInfo.toDomain(): Place {
    return Place(
        id = id,
        startTime = startTime,
        targetDate = targetDate,
        memo = memo,
        spotName = spotName,
        latitude = latitude,
        longitude = longitude,
        category = category
    )
}