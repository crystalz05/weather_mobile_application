package com.tyro.weatherapplication.data.repository

import com.tyro.weatherapplication.BuildConfig
import com.tyro.weatherapplication.data.WeatherResponse
import com.tyro.weatherapplication.data.remote.WeatherApiService
import com.tyro.weatherapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val api: WeatherApiService
) {

    private val apiKey = BuildConfig.WEATHER_API_KEY

    fun getCurrentWeather(location: String): Flow<Resource<WeatherResponse>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.getCurrentWeather(apiKey, location)

            if(response.isSuccessful && response.body() != null){
                emit(Resource.Success(response.body()!!))
            }else {
                emit(Resource.Error("Failed to fetch weather: ${response.message()}"))
            }
        }catch (e: HttpException){
            emit(Resource.Error("Network Error: ${e.localizedMessage}"))
        }catch (e: IOException){
            emit(Resource.Error("No Internet Connection ${e.localizedMessage}"))
        }catch (e: Exception){
            emit(Resource.Error("Unexpected error: ${e.localizedMessage}"))
        }
    }
}