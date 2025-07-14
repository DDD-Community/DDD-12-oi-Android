package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.Place
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    suspend fun queryPlace(query: String): List<Place>
    suspend fun addRecentSearchPlace(place: String)
    fun getRecentSearchPlace(): Flow<Set<String>>
}