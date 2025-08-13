package com.ddd.oi.data.spot.mapper

import com.ddd.oi.data.spot.remote.SpotData
import com.ddd.oi.data.spot.remote.SpotRequest
import com.ddd.oi.domain.model.Spot

fun SpotData.toDomain(): Spot {
    return Spot(
        id = id,
        name = spotName,
        address = address,
        description = spotDescription,
        imageUrl = spotImage,
        latitude = latitude,
        longitude = longitude
    )
}

fun Spot.toRequest(): SpotRequest {
    return SpotRequest(
        spotName = name,
        address = address,
        spotDescription = description,
        spotImage = imageUrl,
        latitude = latitude,
        longitude = longitude
    )
}