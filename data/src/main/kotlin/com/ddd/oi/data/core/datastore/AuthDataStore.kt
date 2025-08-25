package com.ddd.oi.data.core.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.ddd.oi.domain.model.social.SocialType
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "token_pref"
)

@Singleton
class AuthDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object Keys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val USER_ID = longPreferencesKey("user_id")
        val CURRENT_SOCIAL = stringPreferencesKey("current_social")
    }

    suspend fun saveAuthData(
        accessToken: String,
        refreshToken: String,
        userId: Long,
        socialType: SocialType
    ) {
        context.dataStore.edit {
            it[Keys.ACCESS_TOKEN] = accessToken
            it[Keys.REFRESH_TOKEN] = refreshToken
            it[Keys.USER_ID] = userId
            it[Keys.CURRENT_SOCIAL] = socialType.name
        }
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        context.dataStore.edit {
            it[Keys.ACCESS_TOKEN] = accessToken
            it[Keys.REFRESH_TOKEN] = refreshToken
        }
    }

    fun getCurrentLoginType(): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[Keys.CURRENT_SOCIAL]
        }
    }

    fun getUserId(): Flow<Long?> {
        return context.dataStore.data.map { prefs ->
            prefs[Keys.USER_ID]
        }
    }

    suspend fun getAccessToken(): String? {
        return context.dataStore.data.first()[Keys.ACCESS_TOKEN]
    }

    suspend fun getRefreshToken(): String? {
        return context.dataStore.data.first()[Keys.REFRESH_TOKEN]
    }

    suspend fun clear() {
        context.dataStore.edit { preferences ->
            val currentSocialType = preferences[Keys.CURRENT_SOCIAL]
            preferences.clear()
            currentSocialType?.let {
                Log.d("currentSocialType 저장", it)
                preferences[Keys.CURRENT_SOCIAL] = it 
            }
        }
    }
}