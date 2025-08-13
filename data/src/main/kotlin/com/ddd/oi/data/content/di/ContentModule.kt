package com.ddd.oi.data.content.di

import com.ddd.oi.data.content.remote.ContentApi
import com.ddd.oi.data.content.repository.ContentRepositoryImpl
import com.ddd.oi.domain.repository.ContentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContentModule {

    @Provides
    @Singleton
    fun provideContentRepository(
        contentApi: ContentApi
    ): ContentRepository {
        return ContentRepositoryImpl(contentApi)
    }
}