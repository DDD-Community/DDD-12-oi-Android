package com.ddd.oi.data.place.remote

interface PlaceRemoteDataSource {
    suspend fun queryPlace(query: String): PlaceSearchResponse
}