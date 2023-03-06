package com.example.mouvie.dao.favorite

import androidx.room.*
import com.example.mouvie.model.favorite.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_table ORDER BY name ASC")
    fun getFavorites(): Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Favorite) : Long

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAllFavorites(): Int

    @Query("DELETE FROM favorite_table WHERE id_movie = :movieId")
    suspend fun deleteFavorite(movieId: Int): Int

    @Query("SELECT * FROM favorite_table WHERE id_movie = :movieId LIMIT 1")
    fun getFavoriteById(movieId: Int): Flow<Favorite>

}