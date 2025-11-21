package com.tyro.weatherapplication.ui.screens.home.main_screen_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.tyro.weatherapplication.data.HourlyWeather
import com.tyro.weatherapplication.ui.components.WeatherAnimation
import com.tyro.weatherapplication.ui.components.WeatherConditionCard

@Composable
fun CurrentDayData(hourlyListState: LazyListState, hourlyData:  List<HourlyWeather>){

    LazyRow(
        state = hourlyListState,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {

        items(hourlyData) { hour ->

            val currentDayConditionCode = hour.condition.code
            val currentDayAnimation = currentDayConditionCode.let { WeatherAnimation.fromCode(it) }

            val timeLabel = hour.time.split(" ")[1]
            val tempLabel = "${ hour.tempC.toInt()}°"
            WeatherConditionCard(
                currentDayAnimation,
                timeLabel,
                tempLabel)
        }
    }
}