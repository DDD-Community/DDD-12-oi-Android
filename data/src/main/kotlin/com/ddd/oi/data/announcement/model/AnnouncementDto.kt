package com.ddd.oi.data.announcement.model

import kotlinx.serialization.Serializable

@Serializable
data class AnnouncementDto(
    val id: Long? = null,
    val title: String,
    val content: String,
    val updatedAt: String? = null
)

@Serializable
data class AnnouncementPageDto(
    val content: List<AnnouncementDto>,
    val totalElements: Int? = null,
    val totalPages: Int? = null,
    val hasNext: Boolean? = null,
    val pageNumber: Int,
    val pageSize: Int
)