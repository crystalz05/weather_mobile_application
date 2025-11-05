package com.tyro.weatherapplication.data.remote

import com.tyro.weatherapplication.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("Key") apiKey: String,
        @Query("q") location: String,
        @Query("aqi") includeAqi: String = "no"
    ): Response<WeatherResponse>

    suspend fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int = 7,
        @Query("aqi") includeAqi: String = "no"
    ): Response<WeatherResponse>

    @GET("search.json")
    suspend fun searchLocation(
        @Query("key") apiKey: String,
        @Query("location") query: String
    ): Response<WeatherResponse>
}