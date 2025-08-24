package com.ddd.oi.di

import com.ddd.oi.domain.repository.ContentRepository
import com.ddd.oi.domain.repository.ScheduleRepository
import com.ddd.oi.domain.usecase.content.GetContentByIdUseCase
import com.ddd.oi.domain.usecase.content.GetContentsUseCase
import com.ddd.oi.domain.usecase.schedule.GetWeeklySchedulesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetContentsUseCase(
        contentRepository: ContentRepository
    ): GetContentsUseCase {
        return GetContentsUseCase(contentRepository)
    }

    @Provides
    @Singleton
    fun provideGetContentByIdUseCase(
        contentRepository: ContentRepository
    ): GetContentByIdUseCase {
        return GetContentByIdUseCase(contentRepository)
    }

    @Provides
    @Singleton
    fun provideGetWeeklySchedulesUseCase(
        scheduleRepository: ScheduleRepository
    ): GetWeeklySchedulesUseCase {
        return GetWeeklySchedulesUseCase(scheduleRepository)
    }
}