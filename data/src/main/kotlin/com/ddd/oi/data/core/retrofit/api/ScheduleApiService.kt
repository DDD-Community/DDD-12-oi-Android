package com.ddd.oi.data.core.retrofit.api

import com.ddd.oi.data.core.model.BaseResponse
import com.ddd.oi.data.schedule.model.ScheduleDto
import com.ddd.oi.data.schedule.model.ScheduleRequest
import com.ddd.oi.data.scheduledetail.model.EditPlaceDto
import com.ddd.oi.data.scheduledetail.model.EditPlaceRequest
import com.ddd.oi.data.scheduledetail.model.PlaceDto
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
    suspend fun createSchedule(
        @Header("user-no") userId: Long = 1L,
        @Body request: ScheduleRequest
    ): BaseResponse<ScheduleDto>

    @PUT("api/v1/schedules/{scheduleId}")
    suspend fun updateSchedule(
        @Header("user-no") userId: Long = 1L,
        @Path("scheduleId") scheduleId: Long,
        @Body request: ScheduleRequest
    ): BaseResponse<ScheduleDto>

    @GET("api/v1/schedules/{scheduleId}/details")
    suspend fun getScheduleDetails(
        @Path("scheduleId") scheduleId: Long
    ): BaseResponse<List<PlaceDto>>

    @PUT("api/v1/schedules/{scheduleId}/details/{detailId}")
    suspend fun updateScheduleDetail(
        @Path("scheduleId") scheduleId: Long,
        @Path("detailId") detailId: Long,
        @Body request: EditPlaceRequest
    ): BaseResponse<EditPlaceDto>

    @DELETE("api/v1/schedules/{scheduleId}/details/{detailId}")
    suspend fun deleteScheduleDetail(
        @Path("scheduleId") scheduleId: Long,
        @Path("detailId") detailId: Long
    ): BaseResponse<Boolean>
}