package com.tyro.weatherapplication.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.weatherapplication.data.WeatherResponse
import com.tyro.weatherapplication.data.repository.WeatherRepository
import com.tyro.weatherapplication.utils.Resource
import com.tyro.weatherapplication.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _weatherState = MutableStateFlow<Resource<WeatherResponse>>(Resource.Loading())
    val weatherState: StateFlow<Resource<WeatherResponse>> = _weatherState

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent

    private val _currentLocation = MutableStateFlow<String?>(null)

    //fetches weather data by taking the location in words or coordinates
    fun fetchWeather(location: String, forceRefresh: Boolean = false){
        _currentLocation.value = location
        viewModelScope.launch {
            try {
                weatherRepository.getCurrentWeather(location, forceRefresh).collect { resource ->
                    _weatherState.update { resource }
                }
                _uiEvent.emit(UiEvent.ShowSnackBar("Weather details fetched"))
            }catch (e: UnknownHostException){
                Log.d("WeatherViewModel", "No Internet")
            }catch (e: Exception){
                _uiEvent.emit(UiEvent.ShowSnackBar("An error occurred"))
            }
        }
    }

    //fetch weather by passing in the latitude and longitude
    fun fetchWeatherLatLon(latitude: Double, longitude: Double, forceRefresh: Boolean = false){
        val location = "${latitude},${longitude}"
        fetchWeather(location, forceRefresh)
    }

    fun refresh(){
        _currentLocation.value?.let { location ->
            fetchWeather(location, forceRefresh = true)
        }
    }

}