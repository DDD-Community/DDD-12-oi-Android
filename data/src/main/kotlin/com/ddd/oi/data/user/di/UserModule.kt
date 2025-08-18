package com.ddd.oi.data.user.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ddd.oi.data.setting.SettingRepositoryImpl
import com.ddd.oi.data.user.UserRepositoryImpl
import com.ddd.oi.data.user.local.UserLocalDataSource
import com.ddd.oi.data.user.local.UserLocalDataSourceImpl
import com.ddd.oi.data.user.remote.UserRemoteDataSource
import com.ddd.oi.data.user.remote.UserRemoteDataSourceImpl
import com.ddd.oi.domain.repository.SettingRepository
import com.ddd.oi.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserModule {
    
    @Provides
    @Singleton
    fun provideSettingDataStore(
        @com.ddd.oi.data.core.datastore.SettingDataStoreQualifier dataStore: DataStore<Preferences>
    ): com.ddd.oi.data.core.datastore.SettingDataStore {
        return com.ddd.oi.data.core.datastore.SettingDataStore(dataStore)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class UserBindModule {
    
    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
    
    @Binds
    abstract fun bindUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl
    ): UserRemoteDataSource
    
    @Binds
    abstract fun bindUserLocalDataSource(
        userLocalDataSourceImpl: UserLocalDataSourceImpl
    ): UserLocalDataSource
    
    @Binds
    abstract fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository
}