package com.tyro.weatherapplication.ui.screens.home.main_screen_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Opacity
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbCloudy
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WindPower
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tyro.weatherapplication.ui.components.LottieAnimation
import com.tyro.weatherapplication.ui.components.MainCardContentCards
import com.tyro.weatherapplication.ui.components.WeatherConditionCard
import com.tyro.weatherapplication.ui.components.WeatherDetailsCard

@Composable
fun HomeScreen() {

    data class WeatherDetail(
        val title: String,
        val value: String,
        val icon: ImageVector
    )

    val details = listOf(
        WeatherDetail("Wind", "12 km/h", Icons.Outlined.Air),
        WeatherDetail("Humidity", "65%", Icons.Outlined.Opacity),
        WeatherDetail("Feels Like", "28°", Icons.Outlined.Thermostat),
        WeatherDetail("UV Index", "5", Icons.Outlined.WbSunny),
        WeatherDetail("Pressure", "1013 mb", Icons.Outlined.Speed),
        WeatherDetail("Visibility", "10 km", Icons.Outlined.Visibility),
        WeatherDetail("Visibility", "10 km", Icons.Outlined.Visibility)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.fillMaxWidth()){
            Column {
                Text("Current Location", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onBackground.copy(0.6f))
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Outlined.LocationOn, modifier = Modifier.size(30.dp), contentDescription = "Location", tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(8.dp))
                    Text("Delta State", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                }
            }
            Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search", Modifier.size(30.dp), tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                    ),
                                    start = Offset(0f, 0f),
                                    end = Offset(1000f, 1000f)
                                )
                            )
                            .padding(24.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("Right Now", color = MaterialTheme.colorScheme.background, style = MaterialTheme.typography.titleMedium)
                                Text("72°", color = MaterialTheme.colorScheme.background, style = MaterialTheme.typography.displayLarge, fontWeight = FontWeight.ExtraBold)
                            }
                            Icon(imageVector = Icons.Outlined.WbCloudy, contentDescription = null,
                                tint = MaterialTheme.colorScheme.background.copy(0.8f), modifier = Modifier.size(70.dp))
                        }
                        Text("Partly Cloudy", color = MaterialTheme.colorScheme.background, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(24.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                MainCardContentCards(Icons.Outlined.Visibility, "Visibility", "10 mi")
                            }
                            Spacer(Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                MainCardContentCards(Icons.Outlined.Air, "Wind", "10 mi")
                            }
                        }
                        Spacer(Modifier.height(18.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                MainCardContentCards(Icons.Outlined.Opacity, "Humidity", "10 mi")
                            }
                            Spacer(Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                MainCardContentCards(Icons.Outlined.Thermostat, "Feels Like", "10 mi")
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                Text("Today", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(16.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(6) { _ ->
                        WeatherConditionCard(Icons.Outlined.WbCloudy, "1 PM", "73°")
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text("Details", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.height(16.dp))

            }
            items(details.chunked(2)) { rowItem ->
                Row(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    rowItem.forEach { detail ->
                        WeatherDetailsCard(detail.icon, detail.title, detail.value, Modifier.weight(1f))
                    }
                }
            }
        }
    }
}