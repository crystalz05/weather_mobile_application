package com.tyro.weatherapplication.ui.components

import androidx.compose.foundation.layout.size
import com.tyro.weatherapplication.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.LottieAnimation

sealed class WeatherAnimation(val lottie: Int){

    object Clear : WeatherAnimation(R.raw.weather_sunny)
    object PartlyCloudy : WeatherAnimation(R.raw.weatherpartly_cloudy)
    object Cloudy : WeatherAnimation(R.raw.weatherpartly_cloudy)
    object Rain : WeatherAnimation(R.raw.weather_storm_showers_day)
    object Thunder : WeatherAnimation(R.raw.weather_thunder)
    object Storm : WeatherAnimation(R.raw.weather_storm)
    object Shower : WeatherAnimation(R.raw.weather_partly_shower)
    object Foggy : WeatherAnimation(R.raw.foggy)
    object Windy : WeatherAnimation(R.raw.weather_windy)
    object Snow : WeatherAnimation(R.raw.weather_snow_sunny)
    object Default : WeatherAnimation(R.raw.weather_windy)

    companion object{
        fun fromCode(code: Int): WeatherAnimation{
            return when (code) {
                1000 -> Clear
                1003, 1006 -> PartlyCloudy
                1009 -> Cloudy
                1063, 1150, 1180, 1183, 1186 -> Shower
                1189, 1192, 1195 -> Rain
                1273, 1276, 1279 -> Thunder
                1087, 1282 -> Storm
                1030, 1135 -> Foggy
                1066, 1114, 1210 -> Snow
                1050 -> Windy
                else -> Default
            }
        }
    }
}

@Composable
fun LottieAnimation(animation: Int) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animation))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(100.dp)
    )
}

