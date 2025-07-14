package com.ddd.oi.data.place.local

import kotlinx.coroutines.flow.Flow

interface PlaceLocalDataSource {
    fun getRecentSearchPlace(): Flow<List<String>>
    suspend fun addRecentSearchPlace(place: String)
    suspend fun clearRecentSearchPlace()
}