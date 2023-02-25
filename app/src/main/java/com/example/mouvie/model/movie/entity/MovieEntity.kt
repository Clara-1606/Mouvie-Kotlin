package com.example.mouvie.model.movie.entity

data class MovieEntity (
    val id: Int,
    val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    val releaseDate: String,
    val genreIds: List<Int>,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val backdropPath: String?,
    val popularity: Number,
    val voteCount: Int,
    val voteAverage: Number
    )
