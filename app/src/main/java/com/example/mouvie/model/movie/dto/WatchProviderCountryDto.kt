package com.example.mouvie.model.movie.dto

data class WatchProviderCountryDto(
    val link: String?,
    val flatrate: List<WatchProviderDto>?,
    val rent: List<WatchProviderDto>?,
    val buy: List<WatchProviderDto>?,
)