package com.ddd.oi.di

import com.ddd.oi.domain.repository.ContentRepository
import com.ddd.oi.domain.usecase.content.GetContentsUseCase
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
}