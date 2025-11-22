package com.tyro.weatherapplication.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tyro.weatherapplication.data.remote.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    private val observer: ConnectivityObserver,
    @ApplicationContext private val context: Context): ViewModel() {

        private val _status = MutableStateFlow(ConnectivityObserver.Status.UNAVAILABLE)
        val status: StateFlow<ConnectivityObserver.Status> = _status

    init {
        viewModelScope.launch {
            observer.observe().collect{
                state -> _status.update { state }
            }
        }
    }

}