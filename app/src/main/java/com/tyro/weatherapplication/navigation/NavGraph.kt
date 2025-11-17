package com.tyro.weatherapplication.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
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
fun NavGraph(
    navController: NavHostController,
    padding: PaddingValues,
    snackbarHostState: SnackbarHostState
    ) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = Modifier.padding(padding),
        enterTransition ={ fadeIn(animationSpec = tween(50)) },
        exitTransition ={ fadeOut(animationSpec = tween(50)) },
    ) {
        composable(Screen.Home.route) { HomeScreen(snackbarHostState = snackbarHostState) }
        composable(Screen.Forecast.route) { ForecastScreen(snackbarHostState = snackbarHostState) }
        composable(Screen.Favorites.route) { FavoritesScreen(snackbarHostState = snackbarHostState) }
        composable(Screen.Settings.route) { SettingsScreen(snackbarHostState = snackbarHostState) }
    }
}

