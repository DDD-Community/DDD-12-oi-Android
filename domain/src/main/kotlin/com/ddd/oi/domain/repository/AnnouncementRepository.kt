package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.AnnouncementPage

interface AnnouncementRepository {
    suspend fun getNotices(pageNumber: Int, pageSize: Int): Result<AnnouncementPage>
}