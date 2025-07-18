package com.ddd.oi.data.core.retrofit.di

import com.ddd.oi.data.BuildConfig
import com.ddd.oi.data.core.retrofit.api.ScheduleApiService
import com.ddd.oi.data.place.remote.PlaceApi
import com.ddd.oi.data.scheduledetail.remote.ScheduleDetailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    @OiApiRetrofit
    fun provideOiApiRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideScheduleApiService(
        @OiApiRetrofit retrofit: Retrofit
    ): ScheduleApiService {
        return retrofit.create(ScheduleApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesPlaceApi(
        @OiApiRetrofit retrofit: Retrofit
    ): PlaceApi {
        return retrofit.create(PlaceApi::class.java)
    }

    @Provides
    @Singleton
    fun providesScheduleDetailApi(
        @OiApiRetrofit retrofit: Retrofit
    ): ScheduleDetailApi {
        return retrofit.create(ScheduleDetailApi::class.java)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OiApiRetrofit