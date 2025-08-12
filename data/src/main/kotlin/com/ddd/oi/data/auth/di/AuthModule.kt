package com.ddd.oi.data.auth.di

import com.ddd.oi.data.auth.datasource.AuthLocalDataSource
import com.ddd.oi.data.auth.datasource.AuthRemoteDataSource
import com.ddd.oi.data.auth.datasourceImpl.AuthLocalDataSourceImpl
import com.ddd.oi.data.auth.datasourceImpl.AuthRemoteDataSourceImpl
import com.ddd.oi.data.auth.repository.AuthRepositoryImpl
import com.ddd.oi.domain.repository.AuthRepository
import com.ddd.oi.domain.usecase.auth.GetCurrentSocialTypeUseCase
import com.ddd.oi.domain.usecase.auth.GetCurrentSocialTypeUseCaseImpl
import com.ddd.oi.domain.usecase.auth.LoginUseCase
import com.ddd.oi.domain.usecase.auth.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthLocalDataSource(authLocalDataSourceImpl: AuthLocalDataSourceImpl): AuthLocalDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindLoginUseCase(loginUseCaseImpl: LoginUseCaseImpl): LoginUseCase

    @Binds
    @Singleton
    abstract fun bindGetCurrentSocialTypeUseCase(getCurrentSocialTypeUseCaseImpl: GetCurrentSocialTypeUseCaseImpl): GetCurrentSocialTypeUseCase
}

