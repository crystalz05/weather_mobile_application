package com.tyro.weatherapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tyro.weatherapplication.navigation.Screen


sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    object Home : BottomNavItem("home", "Home", Icons.Outlined.Cloud, Icons.Filled.Cloud)
    object Forecast : BottomNavItem("forecast", "Forecast", Icons.Outlined.CalendarToday, Icons.Filled.CalendarToday)
    object Favorites : BottomNavItem("favorites", "Favorites", Icons.Outlined.FavoriteBorder, Icons.Filled.Favorite)
    object Settings : BottomNavItem("settings", "Settings", Icons.Outlined.Settings, Icons.Filled.Settings)
}


@Composable
fun BottomBar(navController: NavController) {
    val items = mapOf(
        BottomNavItem.Home to Screen.Home.route,
        BottomNavItem.Forecast to Screen.Forecast.route,
        BottomNavItem.Favorites to Screen.Favorites.route,
        BottomNavItem.Settings to Screen.Settings.route
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEach { (tab, screen) ->
            val selected = currentRoute == tab.route
            val interactionSource = remember { MutableInteractionSource() }

            Column(
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        navController.navigate(screen) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if(selected) tab.selectedIcon else tab.icon,
                    contentDescription = tab.title,
                    tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                )
                Text(
                    text = tab.title,
                    color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
