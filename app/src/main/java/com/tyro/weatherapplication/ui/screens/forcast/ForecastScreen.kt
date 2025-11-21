package com.tyro.weatherapplication.ui.screens.forcast

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.WbCloudy
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tyro.weatherapplication.ui.components.DaysForecast
import com.tyro.weatherapplication.ui.components.WeatherAnimation
import com.tyro.weatherapplication.utils.getDayFromFullDate
import com.tyro.weatherapplication.viewModels.WeatherViewModel
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastScreen( weatherViewModel: WeatherViewModel
){

//    val weatherViewmodel: WeatherViewModel = hiltViewModel()
    val weatherState by weatherViewModel.weatherState.collectAsState()

    val weatherData = weatherState.data?.forecast?.forecastDays
    val forecast = weatherData ?: emptyList()

    LaunchedEffect(Unit) {
        delay(300)
        Log.d("ForcastScreen", weatherState.data.toString())
    }

    data class ForecastData(
        val icon: ImageVector,
        val day: String,
        val condition: String,
        val topTemp: String,
        val lowTep: String
    )

    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Outlined.CalendarToday, contentDescription = "Calendar",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text("7-Day Forecast",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(Modifier.height(24.dp))
        }
        items(forecast) { forecast ->

            val conditionCode = forecast.day.condition.code
            val animation = WeatherAnimation.fromCode(conditionCode)

            val avgTempLabel = "${forecast.day.avgTempC.toInt()}°"
            val minTempLabel = "${forecast.day.minTempC.toInt()}°"

            DaysForecast(animation, getDayFromFullDate(forecast.date), forecast.day.condition.text, avgTempLabel, minTempLabel)
        }
//        items(forecasts) { forecast ->
//            DaysForecast(forecast.icon, forecast.day, forecast.condition, forecast.topTemp, forecast.lowTep)
//        }
    }

}