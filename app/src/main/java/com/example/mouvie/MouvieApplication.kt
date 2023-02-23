package com.example.mouvie

import android.app.Application
import com.example.mouvie.database.MouvieRoomDatabase
import com.example.mouvie.repository.favorite.FavoriteRepository

class MouvieApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { MouvieRoomDatabase.getDatabase(this) }
    val repository by lazy { FavoriteRepository(database.favoriteDao()) }
}