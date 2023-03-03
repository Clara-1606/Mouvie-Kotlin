package com.example.mouvie.repository.movie

import com.example.mouvie.model.movie.dto.MovieWatchProvidersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WatchProvidersRepository {

    @GET("/3/movie/{movie_id}/watch/providers")
    suspend fun watchProviders(@Path("movieId") id: Int): Response<MovieWatchProvidersDto>
}