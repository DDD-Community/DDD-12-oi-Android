package com.ddd.oi.data.place.di

import com.ddd.oi.data.core.IoDispatcher
import com.ddd.oi.data.place.PlaceRepositoryImpl
import com.ddd.oi.data.place.remote.NaverPlaceApi
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
        naverPlaceApi: NaverPlaceApi,
        @IoDispatcher dispatcher: CoroutineDispatcher,
    ): PlaceRemoteDataSource {
        return PlaceRemoteDataSourceImpl(dispatcher, naverPlaceApi)
    }

    @Provides
    fun providePlaceRepository(placeRemoteDataSource: PlaceRemoteDataSource): PlaceRepository {
        return PlaceRepositoryImpl(placeRemoteDataSource)
    }
}