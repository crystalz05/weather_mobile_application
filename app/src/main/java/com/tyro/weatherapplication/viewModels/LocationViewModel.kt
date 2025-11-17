package com.tyro.weatherapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.weatherapplication.data.DeviceLocation
import com.tyro.weatherapplication.data.location.LocationManager
import com.tyro.weatherapplication.data.repository.WeatherRepository
import com.tyro.weatherapplication.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationManager: LocationManager,
): ViewModel(){

    private val _locationState = MutableStateFlow<Resource<DeviceLocation>>(Resource.Loading())
    val locationState : StateFlow<Resource<DeviceLocation>> = _locationState

    fun getCurrentLocation(){
        viewModelScope.launch {
            _locationState.value = Resource.Loading()
            val result = locationManager.getCurrentLocation()
            _locationState.value = result
        }
    }

    fun hasLocationPermission(): Boolean {
        return locationManager.hasLocationPermission()
    }
}