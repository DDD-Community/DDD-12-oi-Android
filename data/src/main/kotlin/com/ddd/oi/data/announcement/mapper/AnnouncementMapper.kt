package com.ddd.oi.data.announcement.mapper

import com.ddd.oi.data.announcement.model.AnnouncementDto
import com.ddd.oi.data.announcement.model.AnnouncementPageDto
import com.ddd.oi.domain.model.Announcement
import com.ddd.oi.domain.model.AnnouncementPage

fun AnnouncementDto.toDomain(): Announcement {
    return Announcement(
        id = id ?: 0L,
        title = title,
        content = content,
        createdAt = updatedAt ?: ""
    )
}

fun AnnouncementPageDto.toDomain(): AnnouncementPage {
    return AnnouncementPage(
        announcements = content.map { it.toDomain() },
        totalElements = totalElements ?: 0,
        totalPages = totalPages ?: 0,
        hasNext = hasNext ?: false,
        pageNumber = pageNumber,
        pageSize = pageSize
    )
}