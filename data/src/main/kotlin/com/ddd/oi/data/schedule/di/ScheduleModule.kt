package com.ddd.oi.data.schedule.di

import com.ddd.oi.data.schedule.ScheduleRepositoryImpl
import com.ddd.oi.data.schedule.remote.ScheduleRemoteDataSource
import com.ddd.oi.data.schedule.remote.ScheduleRemoteDataSourceImpl
import com.ddd.oi.domain.repository.ScheduleRepository
import com.ddd.oi.domain.usecase.schedule.DeleteScheduleUseCase
import com.ddd.oi.domain.usecase.schedule.DeleteScheduleUseCaseImpl
import com.ddd.oi.domain.usecase.schedule.GetSchedulesUseCase
import com.ddd.oi.domain.usecase.schedule.GetSchedulesUseCaseImpl
import com.ddd.oi.domain.usecase.schedule.UpdateScheduleUseCase
import com.ddd.oi.domain.usecase.schedule.UpdateScheduleUseCaseImpl
import com.ddd.oi.domain.usecase.schedule.CreateScheduleUseCase
import com.ddd.oi.domain.usecase.schedule.CreateScheduleUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduleModule {

    @Binds
    @Singleton
    abstract fun bindScheduleRepository(scheduleRepositoryImpl: ScheduleRepositoryImpl): ScheduleRepository

    @Binds
    @Singleton
    abstract fun bindScheduleRemoteDataSource(scheduleRemoteDataSourceImpl: ScheduleRemoteDataSourceImpl): ScheduleRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object ScheduleUseCaseModule {

    @Provides
    @Singleton
    fun provideGetSchedulesUseCase(
        scheduleRepository: ScheduleRepository
    ): GetSchedulesUseCase {
        return GetSchedulesUseCaseImpl(scheduleRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteScheduleUseCase(
        scheduleRepository: ScheduleRepository
    ): DeleteScheduleUseCase {
        return DeleteScheduleUseCaseImpl(scheduleRepository)
    }

    @Provides
    @Singleton
    fun provideUploadScheduleUseCase(
        scheduleRepository: ScheduleRepository
    ): CreateScheduleUseCase {
        return CreateScheduleUseCaseImpl(scheduleRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateScheduleUseCase(
        scheduleRepository: ScheduleRepository
    ): UpdateScheduleUseCase {
        return UpdateScheduleUseCaseImpl(scheduleRepository)
    }
}