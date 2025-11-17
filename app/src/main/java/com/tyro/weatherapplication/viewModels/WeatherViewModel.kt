package com.tyro.weatherapplication.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.weatherapplication.data.WeatherResponse
import com.tyro.weatherapplication.data.repository.WeatherRepository
import com.tyro.weatherapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _weatherState = MutableStateFlow<Resource<WeatherResponse>>(Resource.Loading())
    val weatherState: StateFlow<Resource<WeatherResponse>> = _weatherState

    //fetches weather data by taking the location in words or coordinates
    fun fetchWeather(location: String){
        viewModelScope.launch {
            weatherRepository.getCurrentWeather(location).collect{resource ->
                _weatherState.update { resource }
                Log.d("Weather ViewModel", _weatherState.value.data.toString())
            }
        }
    }

    //fetch weather by passing in the latitude and longitude
    fun fetchWeatherLatLon(latitude: Double, longitude: Double){
        val location = "${latitude},${longitude}"
        fetchWeather(location)
    }

}