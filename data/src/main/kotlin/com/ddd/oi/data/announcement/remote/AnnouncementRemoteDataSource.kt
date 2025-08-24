package com.ddd.oi.data.announcement.remote

import com.ddd.oi.data.announcement.model.AnnouncementPageDto

interface AnnouncementRemoteDataSource {
    suspend fun getNotices(pageNumber: Int, pageSize: Int): Result<AnnouncementPageDto>
}