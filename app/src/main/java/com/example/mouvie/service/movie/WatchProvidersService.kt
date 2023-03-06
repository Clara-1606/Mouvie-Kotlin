package com.example.mouvie.service.movie

import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.dto.MovieDto
import com.example.mouvie.model.movie.dto.MovieWatchProvidersDto
import com.example.mouvie.repository.movie.WatchProvidersRepository
import com.example.mouvie.service.HttpService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class WatchProvidersService : HttpService() {

    private val watchProvidersRepository: WatchProvidersRepository by lazy { retro.create(WatchProvidersRepository::class.java) }

    suspend fun getWatchProviders(movieId: Int) : Flow<DataState<MovieWatchProvidersDto>> = flow {
        emit(DataState.Loading)
        try {
            val response = watchProvidersRepository.watchProviders(movieId)
            emit(DataState.Success(response.body()!!))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}