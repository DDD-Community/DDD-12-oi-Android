package com.ddd.oi.data.schedule.remote

import com.ddd.oi.data.core.model.BaseResponse
import com.ddd.oi.data.schedule.model.ScheduleDto
import com.ddd.oi.data.schedule.model.ScheduleRequest

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
    ): BaseResponse<Boolean>



    @POST("api/v1/schedules")
    suspend fun uploadSchedule(
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