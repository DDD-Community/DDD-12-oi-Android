package com.ddd.oi.data.announcement

import android.util.Log
import com.ddd.oi.data.announcement.mapper.toDomain
import com.ddd.oi.data.announcement.remote.AnnouncementRemoteDataSource
import com.ddd.oi.domain.model.AnnouncementPage
import com.ddd.oi.domain.repository.AnnouncementRepository
import javax.inject.Inject

class AnnouncementRepositoryImpl @Inject constructor(
    private val announcementRemoteDataSource: AnnouncementRemoteDataSource
) : AnnouncementRepository {
    
    override suspend fun getNotices(pageNumber: Int, pageSize: Int): Result<AnnouncementPage> {
        Log.d("AnnouncementRepository", "Repository getNotices called with page=$pageNumber, size=$pageSize")
        
        return announcementRemoteDataSource.getNotices(pageNumber, pageSize)
            .map { dto ->
                Log.d("AnnouncementRepository", "Mapping DTO to domain: ${dto.content.size} items")
                dto.toDomain()
            }
            .onSuccess { domainPage ->
                Log.d("AnnouncementRepository", "Repository success: ${domainPage.announcements.size} announcements mapped")
            }
            .onFailure { exception ->
                Log.e("AnnouncementRepository", "Repository failed", exception)
            }
    }
}