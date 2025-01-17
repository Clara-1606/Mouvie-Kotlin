package com.example.mouvie.database

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mouvie.dao.favorite.FavoriteDao
import com.example.mouvie.model.favorite.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.chrono.HijrahChronology.INSTANCE


// Annotates class to be a Room Database with a table (entity) of the Favorite class
@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class MouvieRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MouvieRoomDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope): MouvieRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MouvieRoomDatabase::class.java,
                    "mouvie_database"
                )// Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(MouvieDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


    private class MouvieDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var favoriteDao = database.favoriteDao()

                    // Delete all content here.
                    favoriteDao.deleteAllFavorites()

                    // Add sample words.
                    /*var favorite =
                        Favorite(4, 4, "Test1", "https://media-animation.be/IMG/jpg/image.jpg")
                    favoriteDao.insert(favorite)
                    favorite =
                        Favorite(5, 5, "Test5", "https://media-animation.be/IMG/jpg/image.jpg")
                    favoriteDao.insert(favorite)*/
                }
            }
        }
    }
}