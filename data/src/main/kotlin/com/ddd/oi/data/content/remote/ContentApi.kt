package com.ddd.oi.data.content.remote

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ContentApi {
    
    @GET("api/v1/contents")
    suspend fun getContents(
        @Header("user-no") userId: Long = 1L
    ): Response<ContentListResponse>
    
    @POST("api/v1/contents")
    suspend fun createContent(
        @Header("user-no") userId: Long = 1L,
        @Body contentRequest: ContentRequest
    ): Response<ContentResponse>
    
    @GET("api/v1/contents/{contentsId}")
    suspend fun getContent(
        @Header("user-no") userId: Long = 1L,
        @Path("contentsId") contentsId: Long
    ): Response<ContentResponse>
    
    @POST("api/v1/contents/{contentsId}")
    suspend fun updateContent(
        @Header("user-no") userId: Long = 1L,
        @Path("contentsId") contentsId: Long,
        @Body contentRequest: ContentRequest
    ): Response<ContentResponse>
    
    @DELETE("api/v1/contents/{contentsId}")
    suspend fun deleteContent(
        @Header("user-no") userId: Long = 1L,
        @Path("contentsId") contentsId: Long
    ): Response<ContentDeleteResponse>
}

@Serializable
data class ContentRequest(
    val title: String,
    val description: String?,
    val imageUrl: String?
)

@Serializable
data class ContentListResponse(
    val statusCode: Int?,
    val resultType: String?,
    val data: List<ContentData>?,
    val error: ErrorResponse? = null,
    val message: String?
)

@Serializable
data class ContentResponse(
    val statusCode: Int?,
    val resultType: String?,
    val data: ContentData?,
    val error: ErrorResponse? = null,
    val message: String?
)

@Serializable
data class ContentDeleteResponse(
    val statusCode: Int?,
    val resultType: String?,
    val data: Boolean?,
    val error: ErrorResponse? = null,
    val message: String?
)

@Serializable
data class ContentData(
    val id: Long,
    val title: String,
    val displayDescription: String?,
    val cost: Int?,
    val recommendedSchedule: String?,
    val recommendationScore: Double?,
    val duration: Int?,
    val contentsTag: String?,
    val shortTitle: String?,
    val shortDescription: String?,
    val contentsImage: String?,
    val createdAt: String?,
    val spots: List<ContentSpotData>?,
    val viewCount: Int?
)

@Serializable
data class ContentSpotData(
    val id: Long,
    val spotName: String,
    val address: String,
    val spotDescription: String?,
    val spotImage: String?,
    val latitude: Double?,
    val longitude: Double?
)

@Serializable
data class ErrorResponse(
    val message: String?,
    val details: List<String>?
)