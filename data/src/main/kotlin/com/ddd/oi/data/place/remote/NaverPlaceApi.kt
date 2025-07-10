package com.ddd.oi.data.place.remote

import com.ddd.oi.domain.model.Place
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.UUID

interface NaverPlaceApi {

    @GET("v1/search/local.json")
    suspend fun getPlace(
        @Header("X-Naver-Client-Id") xNaverClientId: String,
        @Header("X-Naver-Client-Secret") xNaverClientSecret: String,
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int,
        @Query("sort") sort: String,
    ): Response<NaverSearchResponse>
}

@Serializable
data class NaverSearchResponse(
    val lastBuildDate: String,
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<NaverSearchItem>
)

@Serializable
data class NaverSearchItem(
    val title: String,
    val link: String,
    val category: String,
    val description: String,
    val telephone: String,
    val address: String,
    val roadAddress: String,
    @SerialName("mapx") val mapX: Int,
    @SerialName("mapy") val mapY: Int
) {
    fun toDomain() = Place(
        id = UUID.nameUUIDFromBytes("$title|$address|$mapX|$mapY".toByteArray()).toString(),
        title = title,
        category = category,
        address = address,
        roadAddress = roadAddress,
        mapX = mapX,
        mapY = mapY
    )
}