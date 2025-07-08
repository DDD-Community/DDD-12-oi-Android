package com.ddd.oi.data.place

import com.ddd.oi.data.place.remote.PlaceRemoteDataSource
import com.ddd.oi.domain.model.Place
import com.ddd.oi.domain.repository.PlaceRepository
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val remoteDataSource: PlaceRemoteDataSource
) : PlaceRepository {

    override suspend fun queryPlace(query: String): List<Place> {
        return remoteDataSource.queryPlace(query).items.map { it.toDomain() }
    }
}