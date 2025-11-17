package com.tyro.weatherapplication.data

data class DeviceLocation(
    val latitude: Double,
    val longitude: Double,
    val city: String? = null,
    val country: String? = null,
)
