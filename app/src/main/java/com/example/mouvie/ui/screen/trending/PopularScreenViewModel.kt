package com.example.mouvie.ui.screen.trending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.MovieResponse
import com.example.mouvie.service.movie.MovieService
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PopularScreenViewModel(
    private val movieService: MovieService = MovieService()
): ViewModel() {

    private val _dataState: MutableLiveData<DataState<MovieResponse>> = MutableLiveData()

    val dataState: LiveData<DataState<MovieResponse>>
        get() = _dataState

    var currentPage = 1

    init {
        getPopularMovies(currentPage, "fr")
    }

    private fun getPopularMovies(pageNumber: Int, language: String) {
        viewModelScope.launch {
            movieService.getPopularMovies(pageNumber, language)
                .onEach { dataState ->
                    _dataState.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }

    fun loadNextPage(){
        ++currentPage
        getPopularMovies(currentPage, "fr")
    }
}
