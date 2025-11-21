package com.tyro.weatherapplication.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.tyro.weatherapplication.viewModels.FavoriteViewModel
import com.tyro.weatherapplication.viewModels.WeatherViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    weatherViewModel: WeatherViewModel,
    favoriteViewModel: FavoriteViewModel,
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
        composable(Screen.Home.route) { HomeScreen(weatherViewModel, favoriteViewModel, snackbarHostState = snackbarHostState) }
        composable(Screen.Forecast.route) { ForecastScreen(weatherViewModel) }
        composable(Screen.Favorites.route) { FavoritesScreen(weatherViewModel, favoriteViewModel, navController) }
        composable(Screen.Settings.route) { SettingsScreen() }
    }
}

