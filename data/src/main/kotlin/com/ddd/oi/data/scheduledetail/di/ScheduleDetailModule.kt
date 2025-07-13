package com.ddd.oi.data.scheduledetail.di

import com.ddd.oi.data.schedule.ScheduleRepositoryImpl
import com.ddd.oi.data.schedule.remote.ScheduleRemoteDataSource
import com.ddd.oi.data.schedule.remote.ScheduleRemoteDataSourceImpl
import com.ddd.oi.data.scheduledetail.ScheduleDetailRepositoryImpl
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteSource
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteSourceImpl
import com.ddd.oi.domain.repository.ScheduleDetailRepository
import com.ddd.oi.domain.repository.ScheduleRepository
import com.ddd.oi.domain.usecase.schedule.GetSchedulesUseCase
import com.ddd.oi.domain.usecase.schedule.GetSchedulesUseCaseImpl
import com.ddd.oi.domain.usecase.scheduledetail.GetScheduleDetailsUseCase
import com.ddd.oi.domain.usecase.scheduledetail.GetScheduleDetailsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduleDetailModule {

    @Binds
    @Singleton
    abstract fun bindScheduleDetailRepository(scheduleDetailRepositoryImpl: ScheduleDetailRepositoryImpl): ScheduleDetailRepository

    @Binds
    @Singleton
    abstract fun bindScheduleDetailRemoteDataSource(scheduleDetailRemoteSourceImpl: ScheduleDetailRemoteSourceImpl): ScheduleDetailRemoteSource
}


@Module
@InstallIn(SingletonComponent::class)
object ScheduleDetailUseCaseModule {

    @Provides
    @Singleton
    fun provideGetScheduleDetailsUseCase(
        scheduleDetailRepository: ScheduleDetailRepository
    ): GetScheduleDetailsUseCase {
        return GetScheduleDetailsUseCaseImpl(scheduleDetailRepository)
    }
}