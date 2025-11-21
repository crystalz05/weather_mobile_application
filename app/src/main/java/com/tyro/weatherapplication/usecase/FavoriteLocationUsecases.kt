

package com.tyro.weatherapplication.usecase

import com.tyro.weatherapplication.data.repository.FavoriteLocationRepository
import com.tyro.weatherapplication.data.room.FavoriteLocation
import com.tyro.weatherapplication.data.room.FavouriteLocationDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFavoriteUseCase @Inject constructor (
    private val repository: FavoriteLocationRepository){
    suspend fun add(location: FavoriteLocation) = repository.addFavorite(location)
}

class RemoveFavoriteUseCase @Inject constructor (
    private val repository: FavoriteLocationRepository){
    suspend fun remove(location: FavoriteLocation) = repository.removeFavorite(location)
}

class GetFavoriteUseCase @Inject constructor (
    private val repository: FavoriteLocationRepository){
    fun get() = repository.getFavorites()
}

