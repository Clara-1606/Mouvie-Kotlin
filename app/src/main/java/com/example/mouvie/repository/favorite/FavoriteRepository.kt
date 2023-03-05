package com.example.mouvie.repository.favorite

import androidx.annotation.WorkerThread
import com.example.mouvie.dao.favorite.FavoriteDao
import com.example.mouvie.model.favorite.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class FavoriteRepository (private val favoriteDao: FavoriteDao) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allFavorites: Flow<List<Favorite>> = favoriteDao.getFavorites()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(favorite: Favorite) {
        favoriteDao.insert(favorite)
    }

    suspend fun delete(favorite: Favorite) {
        withContext(Dispatchers.IO) {
            favoriteDao.deleteFavorite(favorite)
        }

    }
}