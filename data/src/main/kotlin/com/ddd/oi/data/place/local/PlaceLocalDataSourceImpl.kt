package com.ddd.oi.data.place.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ddd.oi.data.core.datastore.DataStoreKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaceLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PlaceLocalDataSource {
    override fun getRecentSearchPlace(): Flow<Set<String>> {
        return dataStore.data.map { pref ->
            pref[DataStoreKey.RECENT_SEARCH_PLACE] ?: hashSetOf()
        }
    }

    override suspend fun addRecentSearchPlace(place: String) {
        dataStore.edit { pref ->
            pref[DataStoreKey.RECENT_SEARCH_PLACE] =
                pref[DataStoreKey.RECENT_SEARCH_PLACE]?.toHashSet()?.apply {
                    this.add(place)
                } ?: hashSetOf()
        }
    }
}