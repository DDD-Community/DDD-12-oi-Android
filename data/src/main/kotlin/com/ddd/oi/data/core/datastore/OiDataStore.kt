package com.ddd.oi.data.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.placePrefDataStore : DataStore<Preferences> by preferencesDataStore("placePrefDataStore")

object DataStoreKey {
    val RECENT_SEARCH_PLACE = stringPreferencesKey("RECENT_SEARCH_PLACE")
}