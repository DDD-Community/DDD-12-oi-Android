package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    suspend fun queryPlace(query: String): List<Place>
    fun getRecentSearchPlace(): Flow<List<String>>
    suspend fun addRecentSearchPlace(place: String)
    suspend fun clearRecentSearchPlace()
}