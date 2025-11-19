package com.tyro.weatherapplication.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SettingsManager {
    val themeModeFlow: Flow<String>
    suspend fun setThemeMode(mode: String)
}

class DataStoreSettingsManager (
    private val dataStore: DataStore<Preferences>
) : SettingsManager {

    override val themeModeFlow: Flow<String> = dataStore.data
        .map { preferences -> preferences[PreferencesKeys.THEME] ?: "SYSTEM" }

    override suspend fun setThemeMode(mode: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME] = mode
        }
    }
}