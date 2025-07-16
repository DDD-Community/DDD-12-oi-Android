package com.ddd.oi.data.scheduledetail.di

import com.ddd.oi.data.scheduledetail.ScheduleDetailRepositoryImpl
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailApi
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteDataSource
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteDataSourceImpl
import com.ddd.oi.domain.repository.ScheduleDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ScheduleDetailModule {
    @Provides
    fun providesRemoteDataSource(
        scheduleDetailApi: ScheduleDetailApi
    ): ScheduleDetailRemoteDataSource {
        return ScheduleDetailRemoteDataSourceImpl(scheduleDetailApi)
    }

    @Provides
    fun providesRepository(
        scheduleDetailRemoteDataSource: ScheduleDetailRemoteDataSource
    ): ScheduleDetailRepository {
        return ScheduleDetailRepositoryImpl(scheduleDetailRemoteDataSource)
    }
}