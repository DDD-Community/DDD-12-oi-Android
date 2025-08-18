package com.ddd.oi.data.announcement.remote

import com.ddd.oi.data.core.model.BaseResponse
import com.ddd.oi.data.announcement.model.AnnouncementPageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AnnouncementApi {
    @GET("api/v1/notices")
    suspend fun getNotices(
        @Query("page") pageNumber: Int,
        @Query("size") pageSize: Int
    ): BaseResponse<AnnouncementPageDto>
}