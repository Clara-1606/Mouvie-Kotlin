package com.example.mouvie.model.movie.dto

data class MovieResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    val total_results: Int,
    val total_pages: Int
)
