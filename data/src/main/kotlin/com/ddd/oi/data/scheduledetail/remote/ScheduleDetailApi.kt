package com.ddd.oi.data.scheduledetail.remote

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ScheduleDetailApi {

    @PUT("api/v1/schedules/{scheduleId}/details/{detailId}")
    suspend fun putScheduleDetail(
        @Header("user-no") userId: Long = 1L,
        @Path("scheduleId") scheduleId: Int,
        @Path("detailId") detailId: Int,
        @Body body: SpotRequest
    ): Response<SpotPutResponse>

    @POST("api/v1/schedules/{scheduleId}/details")
    suspend fun postScheduleDetail(
        @Header("user-no") userId: Long = 1L,
        @Path("scheduleId") scheduleId: Int,
        @Body body: List<SpotRequest>
    ): Response<SpotPostResponse>
}

@Serializable
data class SpotRequest(
    val targetDate: String,
    val memo: String,
    val spotName: String,
    val latitude: Double,
    val longitude: Double,
    val category: String
)

@Serializable
data class SpotPostResponse(
    val statusCode: Int?,
    val resultType: String?,
    val data: List<SpotDetail>?,
    val error: ErrorResponse? = null,
    val message: String?
)

@Serializable
data class SpotPutResponse(
    val statusCode: Int?,
    val resultType: String?,
    val data: SpotDetail?,
    val error: ErrorResponse? = null,
    val message: String?
)

@Serializable
data class SpotDetail(
    val scheduleDetailId: Long?,
    val startTime: String?,
    val targetDate: String?,
    val memo: String?,
    val spotName: String?,
    val latitude: Double?,
    val longitude: Double?,
    val category: String?
)

@Serializable
data class ErrorResponse(
    val message: String?,
    val details: List<String>?
)