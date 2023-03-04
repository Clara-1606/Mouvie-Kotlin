package com.example.mouvie.model.movie.dto

data class CastMemberDto(
    val adult: Boolean,
    val gender: Int?,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val profile_path: String?,
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val order: Int
)
