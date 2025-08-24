package com.ddd.oi.data.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.placePrefDataStore : DataStore<Preferences> by preferencesDataStore("placePrefDataStore")
val Context.settingPrefDataStore : DataStore<Preferences> by preferencesDataStore("settingPrefDataStore")

object DataStoreKey {
    val RECENT_SEARCH_PLACE = stringPreferencesKey("RECENT_SEARCH_PLACE")
    val ANNOUNCEMENT_READ_AT = longPreferencesKey("ANNOUNCEMENT_READ_AT")
}