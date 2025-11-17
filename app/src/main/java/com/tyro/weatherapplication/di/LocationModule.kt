package com.tyro.weatherapplication.di

import android.content.Context
import com.tyro.weatherapplication.data.location.LocationManager
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    fun provideLocationManager(
        @ApplicationContext context: Context
    ): LocationManager{
        return LocationManager(context)
    }
}