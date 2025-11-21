package com.tyro.weatherapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteLocation::class], version = 3)
abstract class FavoriteLocationDataBase: RoomDatabase() {
    abstract fun favoriteLocationDao(): FavouriteLocationDao
}

