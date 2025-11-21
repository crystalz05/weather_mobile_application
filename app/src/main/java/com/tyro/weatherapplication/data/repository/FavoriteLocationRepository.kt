package com.tyro.weatherapplication.data.repository

import com.tyro.weatherapplication.data.room.FavoriteLocation
import com.tyro.weatherapplication.data.room.FavouriteLocationDao
import javax.inject.Inject

class FavoriteLocationRepository @Inject constructor(
    private val dao: FavouriteLocationDao
) {
    suspend fun addFavorite(location: FavoriteLocation) = dao.addFavorite(location)
    suspend fun removeFavorite(location: FavoriteLocation) = dao.removeFavorite(location)
    fun getFavorites() = dao.getFavorite()
}