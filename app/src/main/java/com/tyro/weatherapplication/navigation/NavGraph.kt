package com.tyro.weatherapplication.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tyro.weatherapplication.ui.components.BottomNavItem
import com.tyro.weatherapplication.ui.screens.favorites.FavoritesScreen
import com.tyro.weatherapplication.ui.screens.forcast.ForecastScreen
import com.tyro.weatherapplication.ui.screens.home.main_screen_components.HomeScreen
import com.tyro.weatherapplication.ui.screens.settings.SettingsScreen


@Composable
fun NavGraph(navController: NavHostController, padding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = Modifier.padding(padding),
        enterTransition ={ fadeIn(animationSpec = tween(50)) },
        exitTransition ={ fadeOut(animationSpec = tween(50)) },
    ) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Forecast.route) { ForecastScreen() }
        composable(Screen.Favorites.route) { FavoritesScreen() }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}

