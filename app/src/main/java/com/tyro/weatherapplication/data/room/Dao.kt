package com.tyro.weatherapplication.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(location: FavoriteLocation)

    @Delete
    suspend fun removeFavorite(location: FavoriteLocation)

    @Query("SELECT * FROM favorite_locations")
    fun getFavorite(): Flow<List<FavoriteLocation>>

}