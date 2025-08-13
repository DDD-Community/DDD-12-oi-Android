package com.ddd.oi.data.spot.di

import com.ddd.oi.data.spot.remote.SpotApi
import com.ddd.oi.data.spot.repository.SpotRepositoryImpl
import com.ddd.oi.domain.repository.SpotRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpotModule {

    @Provides
    @Singleton
    fun provideSpotRepository(
        spotApi: SpotApi
    ): SpotRepository {
        return SpotRepositoryImpl(spotApi)
    }
}