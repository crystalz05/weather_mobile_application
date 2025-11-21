package com.tyro.weatherapplication.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.weatherapplication.data.room.FavoriteLocation
import com.tyro.weatherapplication.usecase.AddFavoriteUseCase
import com.tyro.weatherapplication.usecase.GetFavoriteUseCase
import com.tyro.weatherapplication.usecase.RemoveFavoriteUseCase
import com.tyro.weatherapplication.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val addFavorite: AddFavoriteUseCase,
    private val removeFavorite: RemoveFavoriteUseCase,
    private val getFavorites: GetFavoriteUseCase
): ViewModel(){

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent

    val favorites: StateFlow<List<FavoriteLocation>> = getFavorites.get()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun add(location: FavoriteLocation) = viewModelScope.launch {
        try{
            addFavorite.add(location)
            _uiEvent.emit(UiEvent.ShowSnackBar("Added to Favorite"))
        }catch (e: Exception){
            Log.d("favorite view model", e.toString())
            _uiEvent.emit(UiEvent.ShowSnackBar("Failed to add location"))
        }
    }

    fun remove(location: FavoriteLocation) = viewModelScope.launch {
        removeFavorite.remove(location)
    }
}