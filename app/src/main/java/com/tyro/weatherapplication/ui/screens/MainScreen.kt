package com.tyro.weatherapplication.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.tyro.weatherapplication.data.remote.ConnectivityObserver
import com.tyro.weatherapplication.navigation.NavGraph
import com.tyro.weatherapplication.ui.components.BottomBar
import com.tyro.weatherapplication.utils.Resource
import com.tyro.weatherapplication.utils.UiEvent
import com.tyro.weatherapplication.viewModels.ConnectivityViewModel
import com.tyro.weatherapplication.viewModels.FavoriteViewModel
import com.tyro.weatherapplication.viewModels.ThemeViewModel
import com.tyro.weatherapplication.viewModels.WeatherViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    weatherViewModel: WeatherViewModel,
    connectivityViewModel: ConnectivityViewModel,
    favoriteViewModel: FavoriteViewModel,
) {

    val status by connectivityViewModel.status.collectAsState()

    val online = status == ConnectivityObserver.Status.AVAILABLE

    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    val state = weatherViewModel.weatherState.collectAsState()

    when (val result = state.value) {
        is Resource.Error -> {
            Snackbar {
                Text(result.message ?: "Unknown error")
            }
        }
        else -> {}
    }

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

                    Column(Modifier.padding(innerPadding).fillMaxWidth()) {
                        if(!online) {
                            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Offline, please check your internet", color = Color.Red)
                            }
                            Spacer(Modifier.height(8.dp))
                        }
                        NavGraph(
                            navController = navController,
                            weatherViewModel = weatherViewModel,
                            padding = PaddingValues(0.dp),
                            snackbarHostState = snackbarHostState,
                            favoriteViewModel = favoriteViewModel
                        )

                    }

                }
            }
        }
    }
}