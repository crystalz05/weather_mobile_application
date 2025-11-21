package com.tyro.weatherapplication.ui.components

import com.tyro.weatherapplication.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MainCardContentCards(icon: ImageVector, title: String, value: String) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(0.2f))) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.background)
                Text(title, color = MaterialTheme.colorScheme.background.copy(0.7f), style = MaterialTheme.typography.titleSmall)
            }
            Text(value, color = MaterialTheme.colorScheme.background, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun WeatherConditionCard(animation: WeatherAnimation, time: String, temperature: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(0.2f)),
        modifier = Modifier.width(90.dp).wrapContentHeight(),
        border = BorderStroke(width = 0.4.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.2f)),
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(time, color = MaterialTheme.colorScheme.onBackground.copy(0.5f), style = MaterialTheme.typography.titleSmall)
            LottieAnimation(animation.lottie, 50.dp)
//            Icon(imageVector = icon, contentDescription = null, Modifier.size(30.dp), tint = MaterialTheme.colorScheme.primary)
            Text(temperature, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun WeatherDetailsCard( icon: ImageVector, title: String, value: String, modifier: Modifier) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(0.2f)),
        border = BorderStroke(width = 0.4.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.2f)),
        modifier = modifier.wrapContentHeight()
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = null, Modifier.size(30.dp), tint = MaterialTheme.colorScheme.primary)
            Text(title, color = MaterialTheme.colorScheme.onBackground.copy(0.5f), style = MaterialTheme.typography.titleMedium)
            Text(value, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
        }
    }
}