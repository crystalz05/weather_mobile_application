package com.tyro.weatherapplication.navigation

sealed class Screen(val route: String){

    object Home: Screen("home")
    object Forecast: Screen("forecast")
    object Favorites: Screen("favorites")
    object Settings: Screen("settings")

}