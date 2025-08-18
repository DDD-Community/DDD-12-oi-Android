package com.ddd.oi.data.announcement.di

import com.ddd.oi.data.announcement.AnnouncementRepositoryImpl
import com.ddd.oi.data.announcement.remote.AnnouncementApi
import com.ddd.oi.data.announcement.remote.AnnouncementRemoteDataSource
import com.ddd.oi.data.announcement.remote.AnnouncementRemoteDataSourceImpl
import com.ddd.oi.data.core.retrofit.di.OiApiRetrofit
import com.ddd.oi.domain.repository.AnnouncementRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AnnouncementModule {

    @Binds
    abstract fun bindAnnouncementRepository(
        announcementRepositoryImpl: AnnouncementRepositoryImpl
    ): AnnouncementRepository

    @Binds
    abstract fun bindAnnouncementRemoteDataSource(
        announcementRemoteDataSourceImpl: AnnouncementRemoteDataSourceImpl
    ): AnnouncementRemoteDataSource

    companion object {
        @Provides
        @Singleton
        fun provideAnnouncementApi(@OiApiRetrofit retrofit: Retrofit): AnnouncementApi =
            retrofit.create(AnnouncementApi::class.java)
    }
}