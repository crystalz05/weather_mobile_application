package com.tyro.weatherapplication.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun FavoriteCards(location: String, condition: String, temperature: String){
    var showDelete by remember { mutableStateOf(false) }

    LaunchedEffect(showDelete) {
        if(showDelete){
            delay(2000)
            showDelete = false
        }
    }

    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(0.2f)),
        border = BorderStroke(width = 0.4.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.2f)),
        modifier = Modifier.height(80.dp)
    ) {
        Row(modifier = Modifier.clickable(
            onClick = {showDelete = !showDelete},
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        )
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = null, Modifier.size(30.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(location, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium)
                    Text(condition, color = MaterialTheme.colorScheme.onBackground.copy(0.4f), style = MaterialTheme.typography.titleSmall)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically){
                Column {
                    Text(temperature, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium)
                }
                AnimatedVisibility(visible = showDelete) {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Outlined.Delete, tint = if(showDelete) Color.Red else Color.Transparent, contentDescription = "Delete favorite")
                    }
                }
            }
        }
    }
}


