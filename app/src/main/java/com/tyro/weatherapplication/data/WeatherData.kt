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
    @SerializedName("feelslike_c")
    val feelsLikeC: Double,
    @SerializedName("feelslike_f")
    val feelsLikeF: Double,
    @SerializedName("uv")
    val uv: Double,
    @SerializedName("vis_km")
    val visibilityKm: Double,
    @SerializedName("pressure_mb")
    val pressure: Double
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
    @SerializedName("forecastday")
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
    @SerializedName("maxtemp_c")
    val maxTempC: Double,
    @SerializedName("maxtemp_f")
    val maxTempf: Double,
    @SerializedName("mintemp_c")
    val minTempC: Double,
    @SerializedName("mintemp_f")
    val minTempf: Double,
    @SerializedName("avgtemp_c")
    val avgTempC: Double,
    @SerializedName("avgtemp_f")
    val avgTempf: Double,
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

