package ru.infinity_coder.chatiumapp.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import ru.infinity_coder.chatiumapp.core.App

object AuthTokenPreferences {

    private val Context.authTokenPreferences: DataStore<Preferences> by preferencesDataStore(name = "authToken")
    private val tokenKey = stringPreferencesKey("token")

    suspend fun saveToken(token: String) {
        App.appContext.authTokenPreferences.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    fun getTokenSync(): String? {
        return runBlocking { getToken() }
    }

    private suspend fun getToken(): String? {
        return App.appContext.authTokenPreferences.data
            .map { it[tokenKey] }
            .firstOrNull()
    }
}