package com.tyro.weatherapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.tyro.weatherapplication.data.ThemeMode
import com.tyro.weatherapplication.helper_classes.setStatusBarIconColor
import com.tyro.weatherapplication.ui.screens.MainScreen
import com.tyro.weatherapplication.ui.theme.WeatherApplicationTheme
import com.tyro.weatherapplication.viewModels.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val themeViewModel: ThemeViewModel = hiltViewModel()

            val themeMode by themeViewModel.themeMode.collectAsState()
            val isSystemDark = isSystemInDarkTheme()

            val isDark = when(themeMode){
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
                ThemeMode.SYSTEM -> isSystemDark
            }

            SideEffect {
                setStatusBarIconColor(activity = this, darkIcons = !isDark)
            }

            WeatherApplicationTheme(darkTheme = isDark) {
                MainScreen()
            }
        }
    }
}