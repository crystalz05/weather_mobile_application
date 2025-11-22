package com.tyro.weatherapplication.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import com.google.gson.Gson
import com.tyro.weatherapplication.data.Forecast
import com.tyro.weatherapplication.data.WeatherResponse
import com.tyro.weatherapplication.data.room.FavoriteLocationDataBase
import com.tyro.weatherapplication.data.room.FavouriteLocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSettingsManager(
        dataStore: DataStore<Preferences>
    ): SettingsManager {
        return DataStoreSettingsManager(dataStore)
    }

    @Provides
    @Singleton
    @Named("currentWeather")
    fun provideCurrentWeatherCacheManager(
        dataStore: DataStore<Preferences>
    ): CacheManager<WeatherResponse> {
        return DataStoreCacheManager(
            dataStore = dataStore,
            dataKey = PreferencesKeys.CURRENT_WEATHER_DATA,
            timestampKey = PreferencesKeys.CURRENT_WEATHER_TIMESTAMP,
            serializer = { data -> Gson().toJson(data) },
            deserializer = { json -> Gson().fromJson(json, WeatherResponse::class.java) },
            cacheValidityMinutes = 30  // Cache weather for 30 minutes
        )
    }

    @Provides
    @Singleton
    @Named("forecast")
    fun provideForecastCacheManager(
        dataStore: DataStore<Preferences>
    ): CacheManager<Forecast> {
        return DataStoreCacheManager(
            dataStore = dataStore,
            dataKey = PreferencesKeys.FORECAST_DATA,
            timestampKey = PreferencesKeys.FORECAST_TIMESTAMP,
            serializer = { data -> Gson().toJson(data) },
            deserializer = { json -> Gson().fromJson(json, Forecast::class.java) },
            cacheValidityMinutes = 60  // Cache forecast for 1 hour
        )
    }


    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): FavoriteLocationDataBase{
        return Room.databaseBuilder(
            context,
            FavoriteLocationDataBase::class.java,
            "weather_db")
            .build()
    }

    @Provides
    fun providesFavoriteDao(db: FavoriteLocationDataBase): FavouriteLocationDao{
        return db.favoriteLocationDao()
    }

//
//    // Location Cache Manager
//    @Provides
//    @Singleton
//    @Named("location")
//    fun provideLocationCacheManager(
//        dataStore: DataStore<Preferences>
//    ): CacheManager<List<LocationData>> {
//        return DataStoreCacheManager(
//            dataStore = dataStore,
//            dataKey = PreferencesKeys.LOCATION_DATA,
//            timestampKey = PreferencesKeys.LOCATION_TIMESTAMP,
//            serializer = { data -> Gson().toJson(data) },
//            deserializer = { json ->
//                val type = object : TypeToken<List<LocationData>>() {}.type
//                Gson().fromJson(json, type)
//            },
//            cacheValidityMinutes = 30
//        )
//    }
//
//    // Forecast Cache Manager
//    @Provides
//    @Singleton
//    @Named("forecast")
//    fun provideForecastCacheManager(
//        dataStore: DataStore<Preferences>
//    ): CacheManager<ForecastData> {
//        return DataStoreCacheManager(
//            dataStore = dataStore,
//            dataKey = PreferencesKeys.FORECAST_DATA,
//            timestampKey = PreferencesKeys.FORECAST_TIMESTAMP,
//            serializer = { data -> Gson().toJson(data) },
//            deserializer = { json -> Gson().fromJson(json, ForecastData::class.java) },
//            cacheValidityMinutes = 60
//        )
//    }
//
//    // Current Weather Cache Manager
//    @Provides
//    @Singleton
//    @Named("currentWeather")
//    fun provideCurrentWeatherCacheManager(
//        dataStore: DataStore<Preferences>
//    ): CacheManager<CurrentWeatherData> {
//        return DataStoreCacheManager(
//            dataStore = dataStore,
//            dataKey = PreferencesKeys.CURRENT_WEATHER_DATA,
//            timestampKey = PreferencesKeys.CURRENT_WEATHER_TIMESTAMP,
//            serializer = { data -> Gson().toJson(data) },
//            deserializer = { json -> Gson().fromJson(json, CurrentWeatherData::class.java) },
//            cacheValidityMinutes = 10
//        )
//    }
}