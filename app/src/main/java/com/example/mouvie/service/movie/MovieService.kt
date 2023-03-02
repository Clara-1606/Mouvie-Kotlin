package com.example.mouvie.service.movie

import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.dto.MovieDetailDto
import com.example.mouvie.model.movie.dto.MovieDto
import com.example.mouvie.repository.movie.MovieRepository
import com.example.mouvie.service.HttpService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieService : HttpService() {

    private val movieRepository: MovieRepository by lazy { retro.create(MovieRepository::class.java) }

    suspend fun getPopularMovies(pageNumber: Int, language: String): Flow<DataState<List<MovieDto>>> = flow {
        emit(DataState.Loading)
        try {
            val movieResponse = movieRepository.popularMovies(pageNumber, language)
            emit(DataState.Success(movieResponse.body()?.results!!))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getMovieDetails(movieId: Int, language: String): Flow<DataState<MovieDetailDto>> = flow {
        emit(DataState.Loading)
        try {
            val movieResponse = movieRepository.movieDetails(movieId, language)
            emit(DataState.Success(movieResponse.body()!!))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}