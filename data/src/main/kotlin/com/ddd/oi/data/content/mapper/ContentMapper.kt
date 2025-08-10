package com.ddd.oi.data.content.mapper

import com.ddd.oi.data.content.remote.ContentData
import com.ddd.oi.data.content.remote.ContentRequest
import com.ddd.oi.data.content.remote.ContentSpotData
import com.ddd.oi.domain.model.Content
import com.ddd.oi.domain.model.Spot

fun ContentData.toDomain(): Content {
    return Content(
        id = id,
        title = title,
        displayDescription = displayDescription ?: "",
        cost = cost ?: 0,
        recommendedSchedule = recommendedSchedule ?: "",
        duration = duration ?: 0,
        contentsTag = contentsTag ?: "",
        shortTitle = shortTitle ?: "",
        shortDescription = shortDescription ?: "",
        imageUrl = contentsImage ?: "",
        recommendationScore = recommendationScore ?: 0.0,
        viewCount = viewCount ?: 0,
        createdAt = createdAt ?: "",
        spots = spots?.map { it.toDomain() } ?: emptyList(),
        badge = badge ?: ""
    )
}

fun ContentSpotData.toDomain(): Spot {
    return Spot(
        id = id,
        name = spotName,
        address = address,
        description = spotDescription ?: "",
        imageUrl = spotImage ?: "",
        latitude = latitude,
        longitude = longitude
    )
}

fun Content.toRequest(): ContentRequest {
    return ContentRequest(
        title = title,
        description = displayDescription,
        imageUrl = imageUrl
    )
}