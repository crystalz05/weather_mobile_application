package com.tyro.weatherapplication.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.weatherapplication.data_store.AppSettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themeDataStore: AppSettingsDataStore): ViewModel() {

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode

    init {
        viewModelScope.launch {
            themeDataStore.themeModeFlow.collect{ savedMode ->
                _themeMode.value = when (savedMode){
                    "LIGHT" -> ThemeMode.LIGHT
                    "DARK" -> ThemeMode.DARK
                    else -> ThemeMode.SYSTEM
                }
            }
        }
    }

    fun setThemeMode(mode: ThemeMode){
        viewModelScope.launch {
            themeDataStore.setThemeMode(mode.name)
        }
    }

    fun toggleDarkMode(){
        val newMode = when (_themeMode.value){
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.SYSTEM -> ThemeMode.LIGHT
        }
    }

    fun useSystemTheme() {
        setThemeMode(ThemeMode.SYSTEM)
    }

}



enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}