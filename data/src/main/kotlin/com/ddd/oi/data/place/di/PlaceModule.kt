package com.ddd.oi.data.place.di

import com.ddd.oi.data.core.IoDispatcher
import com.ddd.oi.data.place.PlaceRepositoryImpl
import com.ddd.oi.data.place.remote.PlaceApi
import com.ddd.oi.data.place.remote.PlaceRemoteDataSource
import com.ddd.oi.data.place.remote.PlaceRemoteDataSourceImpl
import com.ddd.oi.domain.repository.PlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object PlaceModule {

    @Provides
    fun providesPlaceRemoteDataSource(
        placeApi: PlaceApi,
        @IoDispatcher dispatcher: CoroutineDispatcher,
    ): PlaceRemoteDataSource {
        return PlaceRemoteDataSourceImpl(dispatcher, placeApi)
    }

    @Provides
    fun providePlaceRepository(placeRemoteDataSource: PlaceRemoteDataSource): PlaceRepository {
        return PlaceRepositoryImpl(placeRemoteDataSource)
    }
}