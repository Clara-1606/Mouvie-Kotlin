package com.example.mouvie

import android.app.Application
import com.example.mouvie.database.MouvieRoomDatabase
import com.example.mouvie.repository.favorite.FavoriteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MouvieApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { MouvieRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { FavoriteRepository(database.favoriteDao()) }
}