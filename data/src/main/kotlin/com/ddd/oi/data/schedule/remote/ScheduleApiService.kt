package com.ddd.oi.data.schedule.remote

import com.ddd.oi.data.core.model.BaseResponse
import com.ddd.oi.data.core.model.EmptyData
import com.ddd.oi.data.schedule.model.ScheduleDto
import com.ddd.oi.data.schedule.model.ScheduleRequestDto

import retrofit2.http.*

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
    ): BaseResponse<EmptyData>



    @POST("api/v1/schedules")
    suspend fun uploadSchedule(
        @Header("user-no") userId: Long = 1L,
        @Body request: ScheduleRequestDto
    ): BaseResponse<ScheduleDto>
}