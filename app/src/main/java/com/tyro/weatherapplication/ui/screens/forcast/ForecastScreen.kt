package com.tyro.weatherapplication.ui.screens.forcast

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tyro.weatherapplication.ui.components.DaysForecast

@Composable
fun ForecastScreen(){

    data class ForecastData(
        val icon: ImageVector,
        val day: String,
        val condition: String,
        val topTemp: String,
        val lowTep: String
    )

    val forecasts = listOf(
        ForecastData(Icons.Outlined.WbCloudy, "Tomorrow", "Partly Cloudy", "72°", "58°"),
        ForecastData(Icons.Outlined.WbCloudy, "Thursday", "Rainy", "68°", "55°"),
        ForecastData(Icons.Outlined.WbCloudy, "Friday", "Sunny", "75°", "60°"),
        ForecastData(Icons.Outlined.WbCloudy, "Saturday", "Cloudy", "70°", "57°"),
        ForecastData(Icons.Outlined.WbCloudy, "Sunday", "Partly Cloudy", "73°", "59°"),
        )
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Outlined.CalendarToday, contentDescription = "Calendar",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text("5-Day Forecast",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(Modifier.height(24.dp))
        }
        items(forecasts) { forecast ->
            DaysForecast(forecast.icon, forecast.day, forecast.condition, forecast.topTemp, forecast.lowTep)
        }
    }

}