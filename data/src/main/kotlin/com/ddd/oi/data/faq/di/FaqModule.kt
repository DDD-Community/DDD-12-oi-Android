package com.ddd.oi.data.faq.di

import com.ddd.oi.data.core.retrofit.di.OiApiRetrofit
import com.ddd.oi.data.faq.FaqRepositoryImpl
import com.ddd.oi.data.faq.remote.FaqApi
import com.ddd.oi.data.faq.remote.FaqRemoteDataSource
import com.ddd.oi.data.faq.remote.FaqRemoteDataSourceImpl
import com.ddd.oi.domain.repository.FaqRepository
import com.ddd.oi.domain.usecase.faq.GetFaqsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FaqModule {
    
    @Binds
    abstract fun bindFaqRepository(
        faqRepositoryImpl: FaqRepositoryImpl
    ): FaqRepository
    
    @Binds
    abstract fun bindFaqRemoteDataSource(
        faqRemoteDataSourceImpl: FaqRemoteDataSourceImpl
    ): FaqRemoteDataSource
    
    
    companion object {
        @Provides
        @Singleton
        fun provideFaqApi(@OiApiRetrofit retrofit: Retrofit): FaqApi {
            return retrofit.create(FaqApi::class.java)
        }
    }
}