package com.example.mouvie.ui.screen.trending

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.dto.MovieDto
import com.example.mouvie.service.movie.MovieService
import com.example.mouvie.ui.widget.movie.MovieCard
import kotlinx.coroutines.launch

class PopularScreenViewModel(
    private val movieService: MovieService = MovieService()
): ViewModel() {

    // Actual movies and TV Shows
    private val _data: MutableLiveData<List<MovieDto>> = MutableLiveData(mutableListOf())
    val data: LiveData<List<MovieDto>> = _data

    // Current data state (loading, success or error)
    private val _dataState: MutableLiveData<DataState<List<MovieDto>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<MovieDto>>> = _dataState

    private var currentPage = 1

    init {
        getPopularMovies(currentPage, "fr")
    }

    private fun getPopularMovies(pageNumber: Int, language: String) {
        viewModelScope.launch {
            movieService.getPopularMovies(pageNumber, language).collect {
                // Update the data state
                _dataState.value = it

                // Update actual data only when request succeeded
                if(it is DataState.Success) {
                    val newValue = ArrayList<MovieDto>()
                    newValue.addAll(_data.value!!)
                    newValue.addAll(it.data)
                    _data.value = newValue
                }
            }
        }
    }

    fun loadNextPage(){
        ++currentPage
        getPopularMovies(currentPage, "fr")
    }
}
