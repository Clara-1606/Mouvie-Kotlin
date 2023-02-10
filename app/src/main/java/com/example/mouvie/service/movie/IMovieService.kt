package com.example.mouvie.service.movie

import com.example.mouvie.model.movie.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IMovieService {

    @GET("/3/movie/popular")
    fun popularMovies(@Query("page") pageNumber: Int = 1,
                      @Query("language") language: String): Call<MovieResponse>
}