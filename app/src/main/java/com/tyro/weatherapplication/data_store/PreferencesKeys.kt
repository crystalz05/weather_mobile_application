package com.tyro.weatherapplication.data_store

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesKeys {
    // Settings Keys
    val THEME = stringPreferencesKey("settings_theme_mode")

    // Cache Keys
    val LOCATION_DATA = stringPreferencesKey("cache_location_data")
    val LOCATION_TIMESTAMP = longPreferencesKey("cache_location_timestamp")

    val FORECAST_DATA = stringPreferencesKey("cache_forecast_data")
    val FORECAST_TIMESTAMP = longPreferencesKey("cache_forecast_timestamp")

    val CURRENT_WEATHER_DATA = stringPreferencesKey("cache_current_weather_data")
    val CURRENT_WEATHER_TIMESTAMP = longPreferencesKey("cache_current_weather_timestamp")

    val TEMP_UNIT_KEY = stringPreferencesKey("temperature_unit")

}