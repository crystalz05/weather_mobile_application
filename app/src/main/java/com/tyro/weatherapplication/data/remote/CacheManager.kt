package com.tyro.weatherapplication.data.remote

interface CacheManager<T> {
    suspend fun get(): T?
    suspend fun save(data: T)
    suspend fun isExpired(): Boolean
    suspend fun clear()
}