package com.tyro.weatherapplication.data.room

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_locations",
    indices = [Index(value = ["name"], unique = true)])
data class FavoriteLocation(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double
)