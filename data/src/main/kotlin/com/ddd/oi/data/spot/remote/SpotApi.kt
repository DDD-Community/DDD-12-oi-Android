package com.ddd.oi.data.spot.remote

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface SpotApi {
    
    @GET("api/v1/contents/{contentsId}/spots")
    suspend fun getSpots(
        @Header("user-no") userId: Long = 1L,
        @Path("contentsId") contentsId: Long
    ): Response<SpotListResponse>
    
    @GET("api/v1/contents/{contentsId}/spots/{spotId}")
    suspend fun getSpot(
        @Header("user-no") userId: Long = 1L,
        @Path("contentsId") contentsId: Long,
        @Path("spotId") spotId: Long
    ): Response<SpotResponse>
    
    @PUT("api/v1/contents/{contentsId}/spots/{spotId}")
    suspend fun updateSpot(
        @Header("user-no") userId: Long = 1L,
        @Path("contentsId") contentsId: Long,
        @Path("spotId") spotId: Long,
        @Body spotRequest: SpotRequest
    ): Response<SpotResponse>
    
    @DELETE("api/v1/contents/{contentsId}/spots/{spotId}")
    suspend fun deleteSpot(
        @Header("user-no") userId: Long = 1L,
        @Path("contentsId") contentsId: Long,
        @Path("spotId") spotId: Long
    ): Response<SpotDeleteResponse>
}

@Serializable
data class SpotRequest(
    val spotName: String,
    val address: String,
    val spotDescription: String?,
    val spotImage: String?,
    val latitude: Double?,
    val longitude: Double?
)

@Serializable
data class SpotListResponse(
    val statusCode: Int?,
    val resultType: String?,
    val data: List<SpotData>?,
    val error: ErrorResponse? = null,
    val message: String?
)

@Serializable
data class SpotResponse(
    val statusCode: Int?,
    val resultType: String?,
    val data: SpotData?,
    val error: ErrorResponse? = null,
    val message: String?
)

@Serializable
data class SpotDeleteResponse(
    val statusCode: Int?,
    val resultType: String?,
    val data: Boolean?,
    val error: ErrorResponse? = null,
    val message: String?
)

@Serializable
data class SpotData(
    val id: Long,
    val spotName: String,
    val address: String,
    val spotDescription: String?,
    val spotImage: String?,
    val latitude: Double?,
    val longitude: Double?,
    val category: String,
)

@Serializable
data class ErrorResponse(
    val message: String?,
    val details: List<String>?
)