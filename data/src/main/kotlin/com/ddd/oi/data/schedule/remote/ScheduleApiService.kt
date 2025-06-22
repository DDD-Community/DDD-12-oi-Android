package com.ddd.oi.data.schedule.remote

import com.ddd.oi.data.core.model.BaseResponse
import com.ddd.oi.data.schedule.model.ScheduleDto
import com.ddd.oi.data.schedule.model.ScheduleRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ScheduleApiService {
    
    @GET("api/v1/schedules/{year}/{month}")
    suspend fun getSchedules(
        @Header("user-no") userId: Long = 1L,
        @Path("year") year: Int,
        @Path("month") month: Int
    ): BaseResponse<List<ScheduleDto>>

    @DELETE("api/v1/schedules/{scheduleId}")
    suspend fun deleteSchedule(
        @Header("user-no") userId: Long = 1L,
        @Path("scheduleId") scheduleId: Long,
    ): BaseResponse<Boolean>



    @POST("api/v1/schedules")
    suspend fun upsertSchedule(
        @Header("user-no") userId: Long = 1L,
        @Body request: ScheduleRequest
    ): BaseResponse<ScheduleDto>

    @PUT("api/v1/schedules/{scheduleId}")
    suspend fun updateSchedule(
        @Header("user-no") userId: Long = 1L,
        @Path("scheduleId") scheduleId: Long,
        @Body request: ScheduleRequest
    ): BaseResponse<ScheduleDto>
}