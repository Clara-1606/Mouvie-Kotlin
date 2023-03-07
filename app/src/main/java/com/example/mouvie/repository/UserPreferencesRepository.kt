package com.example.mouvie.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.mouvie.model.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class UserPreferencesRepository(val context: Context) {
    private object PreferencesKeys {
        val LANGUAGE = stringPreferencesKey("language")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .map { preferences ->
            val language = preferences[PreferencesKeys.LANGUAGE]
            UserPreferences(language.toString())
        }

    suspend fun updateLanguage(language: String){
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.LANGUAGE] = language
        }
    }
}