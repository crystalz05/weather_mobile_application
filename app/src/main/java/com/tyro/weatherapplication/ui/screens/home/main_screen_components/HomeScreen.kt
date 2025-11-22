package com.tyro.weatherapplication.ui.screens.home.main_screen_components

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.Opacity
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbCloudy
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WindPower
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.substring
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.tyro.weatherapplication.ui.components.LottieAnimation
import com.tyro.weatherapplication.ui.components.MainCardContentCards
import com.tyro.weatherapplication.ui.components.WeatherAnimation
import com.tyro.weatherapplication.ui.components.WeatherConditionCard
import com.tyro.weatherapplication.ui.components.WeatherDetailsCard
import com.tyro.weatherapplication.utils.Resource
import com.tyro.weatherapplication.utils.Temperature
import com.tyro.weatherapplication.viewModels.FavoriteViewModel
import com.tyro.weatherapplication.viewModels.LocationViewModel
import com.tyro.weatherapplication.viewModels.MainViewModel
import com.tyro.weatherapplication.viewModels.WeatherViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    weatherViewModel: WeatherViewModel,
    favoriteViewModel: FavoriteViewModel,
    snackbarHostState: SnackbarHostState
) {


    val viewModel: MainViewModel = hiltViewModel()
    //to check for temperature unit setting
    val currentTempUnit = viewModel.currentTemperature
    val temperatureCelsius = currentTempUnit == Temperature.CELSIUS

    val locationViewModel: LocationViewModel = hiltViewModel()

    val weatherState by weatherViewModel.weatherState.collectAsState()
    val locationState by locationViewModel.locationState.collectAsState()



    val currentDayDate = LocalDate.now()

    val todayWeather = weatherState.data?.forecast?.forecastDays?.firstOrNull{ it.date == currentDayDate.toString() }
    val hourlyData = todayWeather?.hours ?: emptyList()


    // Location permissions
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    // Request location on first composition if permissions granted
    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            locationViewModel.getCurrentLocation()
        }
    }

    // Handle location result and fetch weather
    LaunchedEffect(locationState) {
        when (val state = locationState) {
            is Resource.Success -> {
                val location = state.data
                location?.let {
                    val query = "${it.latitude}, ${it.longitude}"
                    weatherViewModel.fetchWeather(query)
                }
            }
            is Resource.Error -> {
                snackbarHostState.showSnackbar(
                    message = state.message ?: "Location error",
                    duration = SnackbarDuration.Short
                )
            }
            else -> { /* Loading - do nothing */ }
        }
    }

    // Handle permission denial
    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (!locationPermissions.allPermissionsGranted && locationPermissions.shouldShowRationale) {
            val result = snackbarHostState.showSnackbar(
                message = "Location permission is needed to show weather for your area",
                actionLabel = "Grant",
                duration = SnackbarDuration.Long
            )
            if (result == SnackbarResult.ActionPerformed) {
                locationPermissions.launchMultiplePermissionRequest()
            }
        }
    }

    var searchLocation = remember { TextFieldState() }
    var showSearch by remember { mutableStateOf(false) }

    data class WeatherDetail(
        val title: String,
        val value: String,
        val icon: ImageVector
    )

    val weatherData = weatherState.data
    val details = listOf(
        WeatherDetail("Wind", "${weatherData?.current?.windKph} km/h", Icons.Outlined.Air),
        WeatherDetail("Humidity", "${weatherData?.current?.humidity}%", Icons.Outlined.Opacity),
        WeatherDetail("Feels Like C", "${weatherData?.current?.feelsLikeC}°C", Icons.Outlined.Thermostat),
        WeatherDetail("Feels Like F", "${weatherData?.current?.feelsLikeF}°F", Icons.Outlined.Thermostat),
        WeatherDetail("UV Index", "${weatherData?.current?.uv}", Icons.Outlined.WbSunny),
        WeatherDetail("Pressure", "${weatherData?.current?.pressure} mb", Icons.Outlined.Speed),
        WeatherDetail("Visibility", "${weatherData?.current?.visibilityKm} km", Icons.Outlined.Visibility),
    )

    val conditionCode = weatherState.data?.current?.condition?.code
    val animation = conditionCode?.let { WeatherAnimation.fromCode(it) }


    //lazy list state to automatically scroll to the current hour so it appears centered.
    val hourlyListState = rememberLazyListState()
    val currentTime = LocalDateTime.now().hour
    val currentHourIndex = hourlyData.indexOfFirst { hour ->
        hour.time.split(" ")[1].substring(0,2).toInt() == currentTime
    }

    LaunchedEffect(hourlyData) {
        if(currentHourIndex != -1){
            delay(200)
            hourlyListState.animateScrollToItem(
                index = currentHourIndex,
                scrollOffset = -150
            )
        }
    }

    when(weatherState){
        is Resource.Loading ->{
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(Modifier.height(16.dp))

                    // Show permission request if needed
                    if (!locationPermissions.allPermissionsGranted) {
                        Text(
                            "Location permission needed",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(0.6f)
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {
                            locationPermissions.launchMultiplePermissionRequest()
                        }) {
                            Icon(Icons.Outlined.LocationOn, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Grant Permission")
                        }
                    }
                }
            }
        }
        is Resource.Success -> {

            val weather = weatherState.data

            weather?.let { data ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(showSearch) {
                            if (showSearch) {
                                detectTapGestures(onTap = { showSearch = false })
                            }
                        }
                ){
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Column {
                                Text(
                                    "Current Location",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onBackground.copy(0.6f)
                                )
                                Spacer(Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.LocationOn,
                                        modifier = Modifier.size(30.dp),
                                        contentDescription = "Location",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        data.location.name,
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                            }
                            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                                // Refresh location button
                                IconButton(onClick = {
                                    if (locationPermissions.allPermissionsGranted) {
                                        locationViewModel.getCurrentLocation()
                                        locationState.data?.let { weatherViewModel.fetchWeatherLatLon(it.latitude, it.longitude, forceRefresh = true) }
                                    } else {
                                        locationPermissions.launchMultiplePermissionRequest()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Outlined.MyLocation,
                                        contentDescription = "Refresh Location",
                                        Modifier.size(30.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                IconButton(onClick = { showSearch = true }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Search,
                                        contentDescription = "Search",
                                        Modifier.size(30.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        LazyColumn(modifier = Modifier.weight(1f)) {
                            item {
                                //The home screen main card
                                if(animation != null){
                                    MainCard(data, favoriteViewModel, animation, temperatureCelsius)
                                }
                                Spacer(Modifier.height(16.dp))
                                Text("Today", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                                Spacer(Modifier.height(16.dp))
                                CurrentDayData(hourlyListState, hourlyData)
                                Spacer(Modifier.height(16.dp))
                                Text("Details", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
                                Spacer(Modifier.height(16.dp))

                            }
                            items(details.chunked(2)) { rowItem ->
                                Row(modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    rowItem.forEach { detail ->
                                        WeatherDetailsCard(detail.icon, detail.title, detail.value, Modifier.weight(1f))
                                    }
                                }
                            }
                        }
                    }

                    //the search box
                    if(showSearch){
                        Box(modifier = Modifier
                            .background(
                                shape = RoundedCornerShape(100),
                                color = MaterialTheme.colorScheme.background
                            )
                            .height(50.dp)
                            .fillMaxWidth()
                            .padding(start = 18.dp, end = 8.dp)){

                            BasicTextField(state = searchLocation,
                                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                                lineLimits = TextFieldLineLimits.SingleLine,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center))
                            TextButton(onClick = {
                                weatherViewModel.fetchWeather("${searchLocation.text}", forceRefresh = true)
                                showSearch = false
                            }, modifier = Modifier.align(Alignment.CenterEnd)) {
                                Text("Search", color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }
        is Resource.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = weatherState.message ?: "Unknown error",
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.height(16.dp))

                    // Show permission button if that's the issue
                    if (!locationPermissions.allPermissionsGranted) {
                        Button(onClick = {
                            locationPermissions.launchMultiplePermissionRequest()
                        }) {
                            Icon(Icons.Outlined.LocationOn, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Grant Location Permission")
                        }
                    } else {
                        Button(onClick = {
                            locationViewModel.getCurrentLocation()
                        }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}