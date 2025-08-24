package com.ddd.oi.domain.model

data class Announcement(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: String
)

data class AnnouncementPage(
    val announcements: List<Announcement>,
    val totalElements: Int,
    val totalPages: Int,
    val hasNext: Boolean,
    val pageNumber: Int,
    val pageSize: Int
)