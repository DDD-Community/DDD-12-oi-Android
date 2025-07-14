package com.ddd.oi.data.place

import com.ddd.oi.data.place.local.PlaceLocalDataSource
import com.ddd.oi.data.place.remote.PlaceRemoteDataSource
import com.ddd.oi.domain.model.Place
import com.ddd.oi.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    private val localDataSource: PlaceLocalDataSource,
    private val remoteDataSource: PlaceRemoteDataSource
) : PlaceRepository {

    override suspend fun queryPlace(query: String): List<Place> {
        return remoteDataSource.queryPlace(query).data.items.map { it.toDomain() }
    }

    override suspend fun addRecentSearchPlace(place: String) {
        localDataSource.addRecentSearchPlace(place)
    }

    override fun getRecentSearchPlace(): Flow<Set<String>> {
        return localDataSource.getRecentSearchPlace()
    }

    override suspend fun clearRecentSearchPlace() {
        localDataSource.clearRecentSearchPlace()
    }
}