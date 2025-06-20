package com.ddd.oi.data.schedule.di

import com.ddd.oi.data.schedule.ScheduleRepositoryImpl
import com.ddd.oi.data.schedule.remote.ScheduleRemoteDataSource
import com.ddd.oi.data.schedule.remote.ScheduleRemoteDataSourceImpl
import com.ddd.oi.domain.repository.ScheduleRepository
import com.ddd.oi.domain.usecase.GetSchedulesUseCase
import com.ddd.oi.domain.usecase.GetSchedulesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduleModule {

    @Binds
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl
    ): ScheduleRepository

    @Binds
    abstract fun bindScheduleRemoteDataSource(
        scheduleRemoteDataSourceImpl: ScheduleRemoteDataSourceImpl
    ): ScheduleRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetSchedulesUseCase(
        scheduleRepository: ScheduleRepository
    ): GetSchedulesUseCase {
        return GetSchedulesUseCaseImpl(scheduleRepository)
    }
}