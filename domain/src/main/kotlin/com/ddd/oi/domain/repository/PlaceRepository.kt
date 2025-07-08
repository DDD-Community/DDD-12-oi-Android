package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.Place

interface PlaceRepository {
    suspend fun queryPlace(query: String): List<Place>
}