package com.example.mouvie.repository.movie

import com.example.mouvie.model.movie.MovieResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRepository {

    @GET("/3/movie/popular")
    suspend fun popularMovies(@Query("page") pageNumber: Int = 1,
                      @Query("language") language: String): Response<MovieResponse>
}