package com.tyro.weatherapplication.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.tyro.weatherapplication.navigation.NavGraph
import com.tyro.weatherapplication.ui.components.BottomBar
import com.tyro.weatherapplication.utils.Resource
import com.tyro.weatherapplication.utils.UiEvent
import com.tyro.weatherapplication.viewModels.FavoriteViewModel
import com.tyro.weatherapplication.viewModels.ThemeViewModel
import com.tyro.weatherapplication.viewModels.WeatherViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    weatherViewModel: WeatherViewModel,
    favoriteViewModel: FavoriteViewModel,
) {

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    val uiEventFlow = favoriteViewModel.uiEvent

    LaunchedEffect(Unit) {
        uiEventFlow.collect{ event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                else -> {}
            }
        }
    }

    Scaffold(

        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)
        ) {
            Column(modifier = Modifier.fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(0.2f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    NavGraph(
                        navController = navController,
                        weatherViewModel = weatherViewModel,
                        padding = innerPadding,
                        snackbarHostState = snackbarHostState,
                        favoriteViewModel = favoriteViewModel
                    )
                }
            }
        }
    }
}