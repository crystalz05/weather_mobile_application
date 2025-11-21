package com.tyro.weatherapplication.ui.screens.home.main_screen_components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Opacity
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tyro.weatherapplication.data.WeatherResponse
import com.tyro.weatherapplication.data.room.FavoriteLocation
import com.tyro.weatherapplication.ui.components.LottieAnimation
import com.tyro.weatherapplication.ui.components.MainCardContentCards
import com.tyro.weatherapplication.ui.components.WeatherAnimation
import com.tyro.weatherapplication.viewModels.FavoriteViewModel
import com.tyro.weatherapplication.viewModels.LocationViewModel

@Composable
fun MainCard(data: WeatherResponse, favoriteViewModel: FavoriteViewModel, animation: WeatherAnimation) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        TextButton(onClick = {
            favoriteViewModel.add(FavoriteLocation(name=data.location.name, latitude = data.location.lat, longitude = data.location.lon))
                             },
            modifier = Modifier,
            shape = RoundedCornerShape(20))
        { Text("Add to favorite") }
    }
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "Right Now",
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "${data.current.tempC.toInt()}°",
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                LottieAnimation(animation.lottie, 100.dp)
            }
            Text(
                data.current.condition.text,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    MainCardContentCards(
                        Icons.Outlined.Visibility,
                        "Visibility",
                        "${data.current.visibilityKm}"
                    )
                }
                Spacer(Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    MainCardContentCards(
                        Icons.Outlined.Air,
                        "Wind",
                        "${data.current.windKph}"
                    )
                }
            }
            Spacer(Modifier.height(18.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    MainCardContentCards(
                        Icons.Outlined.Opacity,
                        "Humidity",
                        "${data.current.humidity}"
                    )
                }
                Spacer(Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    MainCardContentCards(
                        Icons.Outlined.Thermostat,
                        "Feels Like",
                        "${data.current.feelsLikeC}"
                    )
                }
            }
        }
    }

}