package com.example.mouvie.model.movie.dto

data class MovieCreditsDto(
    val id: Int,
    val cast: List<CastMemberDto>,
    val crew: List<CrewMemberDto>
)
