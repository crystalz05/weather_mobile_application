package com.tyro.weatherapplication.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tyro.weatherapplication.data_store.DataStoreSettingsManager
import com.tyro.weatherapplication.data_store.SettingsManager
import com.tyro.weatherapplication.utils.Temperature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val temperaturePreferences: SettingsManager
): ViewModel() {

    var locationName by mutableStateOf("")
        private set

    var locationLat by mutableStateOf("")
        private set

    var locationLon by mutableStateOf("")
        private set

    var currentTemperature by mutableStateOf(Temperature.CELSIUS)

    fun updateLocationName(value: String){
        locationName = value
    }

    fun updateLocationLat(value: String){
        locationLat = value
    }

    fun updateLocationLon(value: String){
        locationLon = value
    }



    init {
        viewModelScope.launch {
            temperaturePreferences.tempFlow.collect { savedValue ->
                currentTemperature = savedValue
            }
        }
    }

    fun updateCurrentTemperatureType(temperature: Temperature){
        currentTemperature = temperature
        viewModelScope.launch {
            temperaturePreferences.setTemperature(temperature)
        }
    }


}