package com.tyro.weatherapplication.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.tyro.weatherapplication.utils.Temperature
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SettingsManager {
    val themeModeFlow: Flow<String>
    suspend fun setThemeMode(mode: String)
    val tempFlow: Flow<Temperature>
    suspend fun setTemperature(temp: Temperature)
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

    override val tempFlow: Flow<Temperature> = dataStore.data.map { preferences ->
        val value = preferences[PreferencesKeys.TEMP_UNIT_KEY]
        if(value == Temperature.FAHRENHEIT.name) Temperature.FAHRENHEIT
        else Temperature.CELSIUS
    }

    override suspend fun setTemperature(temp: Temperature) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TEMP_UNIT_KEY] = temp.name
        }
    }
}