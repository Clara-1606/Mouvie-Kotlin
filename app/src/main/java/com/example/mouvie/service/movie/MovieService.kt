package com.example.mouvie.service.movie

import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.dto.MovieDto
import com.example.mouvie.model.movie.dto.MovieResponseDto
import com.example.mouvie.repository.movie.MovieRepository
import com.example.mouvie.service.HttpService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieService : HttpService() {

    private val movieService: MovieRepository by lazy { retro.create(MovieRepository::class.java) }

    suspend fun getPopularMovies(pageNumber: Int, language: String): Flow<DataState<List<MovieDto>>> = flow {
        emit(DataState.Loading)
        try {
            val movieResponse = movieService.popularMovies(pageNumber, language)
            emit(DataState.Success(movieResponse.body()?.results!!))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}