package com.tyro.weatherapplication.data

import com.google.gson.annotations.SerializedName


data class WeatherResponse(
    @SerializedName("location")
    val location: Location,
    @SerializedName("current")
    val current: CurrentWeather,
    @SerializedName("forecast")
    val forecast: Forecast
)

data class Location(
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)

data class CurrentWeather(
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("feels_like_c")
    val feelsLikeC: Double,
    @SerializedName("uv")
    val uv: Double,
    @SerializedName("vis-km")
    val visibilityKm: Double
)

data class Condition(
    @SerializedName("text")
    val text: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("code")
    val code: Int
)


data class Forecast(
    @SerializedName("forecast_day")
    val forecastDays: List<ForecastDay>
)

data class ForecastDay(
    @SerializedName("date")
    val date: String,
    @SerializedName("day")
    val day: Day,
    @SerializedName("hour")
    val hours: List<HourlyWeather>
)

data class Day(
    @SerializedName("max_temp_c")
    val maxTempC: Double,
    @SerializedName("max_temp_f")
    val maxTempf: Double,
    @SerializedName("condition")
    val condition: Condition
)

data class HourlyWeather(
    @SerializedName("time")
    val time: String,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("condition")
    val condition: Condition
)

