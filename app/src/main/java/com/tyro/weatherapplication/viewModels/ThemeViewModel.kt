package com.tyro.weatherapplication.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.weatherapplication.data.ThemeMode
import com.tyro.weatherapplication.data_store.SettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val settingsManager: SettingsManager): ViewModel() {

    val themeMode: StateFlow<ThemeMode> = settingsManager.themeModeFlow
        .map { savedMode ->
            when (savedMode) {
                "LIGHT" -> ThemeMode.LIGHT
                "DARK" -> ThemeMode.DARK
                else -> ThemeMode.SYSTEM
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeMode.SYSTEM
        )

    fun setThemeMode(mode: ThemeMode){
        viewModelScope.launch {
            settingsManager.setThemeMode(mode.name)
        }
    }

    fun toggleDarkMode(){
        val newMode = when (themeMode.value){
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.SYSTEM -> ThemeMode.LIGHT
        }
        setThemeMode(newMode)  //apply the change
    }

    fun useSystemTheme() {
        setThemeMode(ThemeMode.SYSTEM)
    }

}