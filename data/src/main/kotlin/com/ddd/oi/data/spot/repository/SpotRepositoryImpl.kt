package com.ddd.oi.data.spot.repository

import com.ddd.oi.data.spot.mapper.toDomain
import com.ddd.oi.data.spot.mapper.toRequest
import com.ddd.oi.data.spot.remote.SpotApi
import com.ddd.oi.domain.model.Spot
import com.ddd.oi.domain.repository.SpotRepository
import javax.inject.Inject

class SpotRepositoryImpl @Inject constructor(
    private val spotApi: SpotApi
) : SpotRepository {
    
    override suspend fun getSpots(contentsId: Long, userId: Long): List<Spot> {
        val response = spotApi.getSpots(userId, contentsId)
        if (response.isSuccessful) {
            return response.body()?.data?.map { it.toDomain() } ?: emptyList()
        } else {
            throw Exception("Failed to get spots: ${response.errorBody()?.string()}")
        }
    }
    
    override suspend fun getSpot(contentsId: Long, spotId: Long, userId: Long): Spot {
        val response = spotApi.getSpot(userId, contentsId, spotId)
        if (response.isSuccessful) {
            return response.body()?.data?.toDomain() 
                ?: throw IllegalStateException("Spot not found")
        } else {
            throw Exception("Failed to get spot: ${response.errorBody()?.string()}")
        }
    }
    
    override suspend fun updateSpot(contentsId: Long, spotId: Long, spot: Spot, userId: Long): Spot {
        val response = spotApi.updateSpot(userId, contentsId, spotId, spot.toRequest())
        if (response.isSuccessful) {
            return response.body()?.data?.toDomain()
                ?: throw IllegalStateException("Failed to update spot")
        } else {
            throw Exception("Failed to update spot: ${response.errorBody()?.string()}")
        }
    }
    
    override suspend fun deleteSpot(contentsId: Long, spotId: Long, userId: Long): Boolean {
        val response = spotApi.deleteSpot(userId, contentsId, spotId)
        if (response.isSuccessful) {
            return response.body()?.data ?: false
        } else {
            throw Exception("Failed to delete spot: ${response.errorBody()?.string()}")
        }
    }
}