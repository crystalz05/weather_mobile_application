package com.tyro.weatherapplication.data.repository

import android.util.Log
import com.tyro.weatherapplication.BuildConfig
import com.tyro.weatherapplication.data.CurrentWeather
import com.tyro.weatherapplication.data.Forecast
import com.tyro.weatherapplication.data.Location
import com.tyro.weatherapplication.data.WeatherResponse
import com.tyro.weatherapplication.data.remote.WeatherApiService
import com.tyro.weatherapplication.data_store.CacheManager
import com.tyro.weatherapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val api: WeatherApiService,
    @Named("currentWeather") private val weatherCache: CacheManager<WeatherResponse>
) {

    private val apiKey = BuildConfig.WEATHER_API_KEY

    fun getCurrentWeather(location: String, forceRefresh: Boolean = false
    ): Flow<Resource<WeatherResponse>> = flow {
        emit(Resource.Loading())

        try {

            if(!forceRefresh && !weatherCache.isExpired()){
                weatherCache.get()?.let { cacheWeather ->
                    emit(Resource.Success(cacheWeather))
                    return@flow
                }
            }

            val response = api.getForecast(apiKey, location)

            if(response.isSuccessful && response.body() != null){
                val weatherData = response.body()!!

                //save to cache
                weatherCache.save(weatherData)
                emit(Resource.Success(weatherData))
            }else {
                //API failed, try returning stale cache
                weatherCache.get()?.let { staleData ->
                    emit(Resource.Success(staleData))
                } ?: emit(Resource.Error("Failed to fetch weather: ${response.message()}"))
            }
        }catch (e: HttpException){
            weatherCache.get()?.let { staleData ->
                emit(Resource.Success(staleData))
            }?: emit(Resource.Error("Network Error: ${e.localizedMessage}"))
        }catch (e: IOException){
            weatherCache.get()?.let { staleData ->
                emit(Resource.Success(staleData))
            }?: emit(Resource.Error("No Internet Connection ${e.localizedMessage}"))
        }catch (e: Exception){
            weatherCache.get()?.let { staleData ->
                emit(Resource.Success(staleData))
            }?: emit(Resource.Error("Unexpected error: ${e.localizedMessage}"))
        }
    }
}