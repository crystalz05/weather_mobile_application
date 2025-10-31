package com.tyro.weatherapplication.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
fun DaysForecast(icon: ImageVector, day: String, condition: String, topTemp: String, lowTemp:String){
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(0.2f)),
        border = BorderStroke(width = 0.4.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.2f)),
        modifier = Modifier.wrapContentHeight()
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, Modifier.size(30.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(day, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium)
                    Text(condition, color = MaterialTheme.colorScheme.onBackground.copy(0.4f), style = MaterialTheme.typography.titleSmall)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(topTemp, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium)
                Text(lowTemp, color = MaterialTheme.colorScheme.onBackground.copy(0.4f), style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}