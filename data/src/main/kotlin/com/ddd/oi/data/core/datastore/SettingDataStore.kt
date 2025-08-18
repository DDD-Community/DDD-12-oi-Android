package com.ddd.oi.data.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    
    fun getAnnouncementReadAt(): Flow<Long> {
        return dataStore.data.map { preferences ->
            preferences[DataStoreKey.ANNOUNCEMENT_READ_AT] ?: 0L
        }
    }
    
    suspend fun setAnnouncementReadAt(timestamp: Long) {
        dataStore.edit { preferences ->
            preferences[DataStoreKey.ANNOUNCEMENT_READ_AT] = timestamp
        }
    }
}