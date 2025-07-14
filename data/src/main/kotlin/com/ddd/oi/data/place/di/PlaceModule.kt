package com.ddd.oi.data.place.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ddd.oi.data.core.IoDispatcher
import com.ddd.oi.data.core.datastore.PlaceDataStore
import com.ddd.oi.data.place.PlaceRepositoryImpl
import com.ddd.oi.data.place.local.PlaceLocalDataSource
import com.ddd.oi.data.place.local.PlaceLocalDataSourceImpl
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
    fun providesPlaceLocalDataSource(
        @PlaceDataStore dataStore: DataStore<Preferences>
    ): PlaceLocalDataSource {
        return PlaceLocalDataSourceImpl(dataStore)
    }

    @Provides
    fun providePlaceRepository(
        placeLocalDataSource: PlaceLocalDataSource,
        placeRemoteDataSource: PlaceRemoteDataSource
    ): PlaceRepository {
        return PlaceRepositoryImpl(placeLocalDataSource, placeRemoteDataSource)
    }
}