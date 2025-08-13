package com.ddd.oi.domain.repository

import com.ddd.oi.domain.model.Spot

interface SpotRepository {
    suspend fun getSpots(contentsId: Long, userId: Long = 1L): List<Spot>
    suspend fun getSpot(contentsId: Long, spotId: Long, userId: Long = 1L): Spot
    suspend fun updateSpot(contentsId: Long, spotId: Long, spot: Spot, userId: Long = 1L): Spot
    suspend fun deleteSpot(contentsId: Long, spotId: Long, userId: Long = 1L): Boolean
}