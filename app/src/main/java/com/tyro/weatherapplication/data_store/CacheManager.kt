package com.tyro.weatherapplication.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

interface CacheManager<T> {
    suspend fun get(): T?
    suspend fun save(data: T)
    suspend fun isExpired(): Boolean
    suspend fun clear()
}

class DataStoreCacheManager<T>(
    private val dataStore: DataStore<Preferences>,
    private val dataKey: Preferences.Key<String>,
    private val timestampKey: Preferences.Key<Long>,
    private val serializer: (T) -> String,
    private val deserializer: (String) -> T,
    private val cacheValidityMinutes: Long = 30
) : CacheManager<T> {

    override suspend fun get(): T? {
        return dataStore.data.map { prefs ->
            prefs[dataKey]?.let { deserializer(it) }
        }.firstOrNull()
    }

    override suspend fun save(data: T) {
        dataStore.edit { prefs ->
            prefs[dataKey] = serializer(data)
            prefs[timestampKey] = System.currentTimeMillis()
        }
    }

    override suspend fun isExpired(): Boolean {
        val timestamp = dataStore.data.map { prefs ->
            prefs[timestampKey]
        }.firstOrNull() ?: return true

        val currentTime = System.currentTimeMillis()
        val validityMillis = cacheValidityMinutes * 60 * 1000
        return (currentTime - timestamp) > validityMillis
    }

    override suspend fun clear() {
        dataStore.edit { prefs ->
            prefs.remove(dataKey)
            prefs.remove(timestampKey)
        }
    }
}