package com.tyro.weatherapplication.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class NetworkConnectivityObserver(context: Context): ConnectivityObserver {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    override fun observe(): Flow<ConnectivityObserver.Status> = callbackFlow {

        val callBack = object : ConnectivityManager.NetworkCallback(){

            override fun onAvailable(network: Network) {
                trySend(ConnectivityObserver.Status.AVAILABLE)
            }
            override fun onLost(network: Network) {
                trySend(ConnectivityObserver.Status.LOST)
            }
            override fun onLosing(network: Network, maxMsToLive: Int) {
                trySend(ConnectivityObserver.Status.LOSING)
            }
            override fun onUnavailable() {
                trySend(ConnectivityObserver.Status.UNAVAILABLE)
            }
        }

        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, callBack)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callBack)
        }
    }
}


interface ConnectivityObserver{
    fun observe(): Flow<Status>

    enum class Status {
        AVAILABLE, UNAVAILABLE,LOSING,LOST
    }
}