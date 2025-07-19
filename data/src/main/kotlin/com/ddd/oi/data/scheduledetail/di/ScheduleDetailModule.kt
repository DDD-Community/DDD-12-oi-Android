package com.ddd.oi.data.scheduledetail.di

import com.ddd.oi.data.scheduledetail.ScheduleDetailRepositoryImpl
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteSource
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteSourceImpl
import com.ddd.oi.domain.repository.ScheduleDetailRepository
import com.ddd.oi.domain.usecase.scheduledetail.GetScheduleDetailsUseCase
import com.ddd.oi.domain.usecase.scheduledetail.GetScheduleDetailsUseCaseImpl
import dagger.Binds
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailApi
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteDataSource
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailRemoteDataSourceImpl
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

@Module
@InstallIn(SingletonComponent::class)
object ScheduleDetailModule2 {
    @Provides
    fun providesRemoteDataSource(
        scheduleDetailApi: ScheduleDetailApi
    ): ScheduleDetailRemoteDataSource {
        return ScheduleDetailRemoteDataSourceImpl(scheduleDetailApi)
    }
}