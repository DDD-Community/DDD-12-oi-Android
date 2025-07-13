package com.ddd.oi.data.place.remote

import com.ddd.oi.domain.model.Place
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.UUID

interface PlaceApi {

    @GET("api/search/places")
    suspend fun getPlace(
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int,
        @Query("sort") sort: String,
    ): Response<PlaceSearchResponse>
}

@Serializable
data class PlaceSearchResponse(
    val statusCode: Int,
    val resultType: String,
    val data: PlaceData,
    val message: String
)

@Serializable
data class PlaceData(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<PlaceItem>,
    val hasMore: Boolean
)

@Serializable
data class PlaceItem(
    val link: String,
    val telephone: String,
    val address: String,
    val roadAddress: String,
    @SerialName("mapx") val mapX: Int,
    @SerialName("mapy") val mapY: Int,
    val mainCategory: String,
    val categoryColor: String,
    val title: String,
    val category: String,
    val description: String
) {
    fun toDomain() = Place(
        id = UUID.nameUUIDFromBytes("$title|$address|$mapX|$mapY".toByteArray()).toString(),
        title = title,
        category = mainCategory,
        address = address,
        roadAddress = roadAddress,
        mapX = mapX,
        mapY = mapY,
        categoryColor = categoryColor
    )
}