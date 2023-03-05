package com.example.mouvie.model.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class Favorite (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "id_movie") val idMovie: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "poster_path") val posterPath: String
    )
