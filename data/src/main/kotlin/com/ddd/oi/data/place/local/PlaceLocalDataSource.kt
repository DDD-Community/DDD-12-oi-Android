package com.ddd.oi.data.place.local

import kotlinx.coroutines.flow.Flow

interface PlaceLocalDataSource {
    fun getRecentSearchPlace(): Flow<Set<String>>
    suspend fun addRecentSearchPlace(place: String)
    suspend fun clearRecentSearchPlace()
}