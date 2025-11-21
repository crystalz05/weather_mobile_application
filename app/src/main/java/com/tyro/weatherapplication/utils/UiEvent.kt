package com.tyro.weatherapplication.utils

sealed class UiEvent {
    data class ShowSnackBar(val message: String): UiEvent()
    data object Success: UiEvent()
    data object Loading: UiEvent()
}