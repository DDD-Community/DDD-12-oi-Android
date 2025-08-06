package com.ddd.oi.domain.model

data class Content(
    val id: Long,
    val title: String,
    val displayDescription: String = "",
    val cost: Int = 0,
    val recommendedSchedule: String = "",
    val duration: Int = 0,
    val contentsTag: String = "",
    val shortTitle: String = "",
    val shortDescription: String = "",
    val imageUrl: String = "",
    val recommendationScore: Double = 0.0,
    val viewCount: Int = 0,
    val createdAt: String = "",
    val spots: List<Spot> = emptyList()
)