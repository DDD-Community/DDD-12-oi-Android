package com.ddd.oi.data.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @PlaceDataStore
    fun providesPlaceDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.placePrefDataStore
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlaceDataStore