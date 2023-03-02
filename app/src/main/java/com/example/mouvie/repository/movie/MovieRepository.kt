package com.example.mouvie.repository.movie

import com.example.mouvie.model.movie.dto.MovieDetailDto
import com.example.mouvie.model.movie.dto.MovieResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieRepository {

    @GET("/3/movie/popular")
    suspend fun popularMovies(@Query("page") pageNumber: Int = 1,
                      @Query("language") language: String): Response<MovieResponseDto>

    @GET("/3/movie/{id}")
    suspend fun movieDetails(@Path("id") id: Int, @Query("language") language: String): Response<MovieDetailDto>
}