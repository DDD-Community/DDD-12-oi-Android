package com.ddd.oi.data.announcement.remote

import android.util.Log
import com.ddd.oi.data.announcement.model.AnnouncementPageDto
import com.ddd.oi.data.core.model.safeApiCall
import javax.inject.Inject

class AnnouncementRemoteDataSourceImpl @Inject constructor(
    private val announcementApi: AnnouncementApi
) : AnnouncementRemoteDataSource {
    
    override suspend fun getNotices(pageNumber: Int, pageSize: Int): Result<AnnouncementPageDto> {
        Log.d("AnnouncementRemoteDataSource", "Calling API: /api/v1/notices with page=$pageNumber, size=$pageSize")
        
        return safeApiCall {
            announcementApi.getNotices(pageNumber, pageSize)
        }.onSuccess {
            Log.d("AnnouncementRemoteDataSource", "API call successful: ${it.content.size} announcements received")
        }.onFailure { exception ->
            Log.e("AnnouncementRemoteDataSource", "API call failed", exception)
            Log.e("AnnouncementRemoteDataSource", "Exception type: ${exception::class.java.simpleName}")
            Log.e("AnnouncementRemoteDataSource", "Exception message: ${exception.message}")
        }
    }
}