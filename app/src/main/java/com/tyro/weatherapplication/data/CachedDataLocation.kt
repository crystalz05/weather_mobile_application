package com.tyro.weatherapplication.data


data class CachedDataLocation<T> (
    val data: T,
    val timeStamp: Long
)