package com.tyro.weatherapplication.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.tyro.weatherapplication.data.DeviceLocation
import com.tyro.weatherapplication.utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class LocationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val geocoder = Geocoder(context, Locale.getDefault())

    //Check if location permissions are granted
    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    //Get current location once
    suspend fun getCurrentLocation(): Resource<DeviceLocation> {
        if(!hasLocationPermission()){
            return Resource.Error("Location permission not granted")
        }

        return suspendCancellableCoroutine { continuation ->
            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location ->
                        if(location != null){
                            val cityName = getCityName(location.latitude, location.longitude)
                            val deviceLocation = DeviceLocation(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                city = cityName
                            )
                            continuation.resume(Resource.Success(deviceLocation))
                        }else{
                            //No cached location, request fresh one
                            requestNewLocation { newLocation ->
                                if(newLocation != null){
                                    val cityName = getCityName(newLocation.latitude, newLocation.longitude)
                                    val deviceLocation = DeviceLocation(
                                        latitude = newLocation.latitude,
                                        longitude = newLocation.longitude,
                                        city = cityName
                                    )
                                    continuation.resume(Resource.Success(deviceLocation))
                                }else{
                                    continuation.resume(Resource.Error("Unable to get location"))
                                }
                            }
                        }
                    }.addOnFailureListener { e ->
                        continuation.resume(Resource.Error("Location error : ${e.message}"))
                    }
            }catch (e: SecurityException){
                continuation.resume(Resource.Error("Location permission denied"))
            }
        }
    }

    //Observe location updates (continuous)
    fun observeLocationUpdates(): Flow<Resource<DeviceLocation>> = callbackFlow {
        if(!hasLocationPermission()){
            trySend(Resource.Error("Location permission not granted"))
            close()
            return@callbackFlow
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 3000L
        ).apply {
            setMinUpdateIntervalMillis(15000L)
        }.build()

        val locationCallback = object : LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    val cityName = getCityName(location.latitude, location.longitude)
                    val deviceLocation = DeviceLocation(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        city = cityName
                    )
                    trySend(Resource.Success(deviceLocation))
                }
            }
        }
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

            trySend(Resource.Loading())
        } catch (e: SecurityException){
            trySend(Resource.Error("Location permission denied"))
            close()
        }

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    //Request fresh location update
    private fun requestNewLocation(callback: (android.location.Location?) -> Unit){

        if(!hasLocationPermission()){
            callback(null)
            return
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000L).apply {
                setMinUpdateIntervalMillis(5000L)
            setMaxUpdates(1)
        }.build()

        val locationCallBack = object: LocationCallback(){
            override fun onLocationResult(result: LocationResult) {
                callback(result.lastLocation)
                fusedLocationClient.removeLocationUpdates(this)
            }
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallBack,
                Looper.getMainLooper()
            )
        }catch (e: SecurityException){
            callback(null)
        }
    }

    //Get city name from coordinates using Geocoder
    private fun getCityName(latitude: Double, longitude: Double): String? {
        return try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            addresses?.firstOrNull()?.locality ?: addresses?.firstOrNull()?.adminArea
        } catch (e: Exception) {
            null
        }
    }

}