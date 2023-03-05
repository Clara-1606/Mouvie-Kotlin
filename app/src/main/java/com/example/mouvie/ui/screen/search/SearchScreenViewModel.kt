package com.example.mouvie.ui.screen.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mouvie.config.fixed.UserPreferencesValues
import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.dto.MovieDto
import com.example.mouvie.service.movie.MovieService
import kotlinx.coroutines.launch

class SearchScreenViewModel (
    private val movieService: MovieService = MovieService()
    ) : ViewModel()
{
    // Actual movies and TV Shows
    private val _data: MutableLiveData<List<MovieDto>> = MutableLiveData(mutableListOf())
    val data: LiveData<List<MovieDto>> = _data

    // Current data state (loading, success or error)
    private val _dataState: MutableLiveData<DataState<List<MovieDto>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<MovieDto>>> = _dataState

    private var currentPage = 1

    fun search(query: String, page: Int, isUserInput: Boolean) {
        viewModelScope.launch {
            if (isUserInput) {
                currentPage = page
            }
            movieService.search(query, page, UserPreferencesValues.LANGUAGE).collect {
                // Update the data state
                _dataState.value = it

                // Update actual data only when request succeeded
                if(it is DataState.Success) {
                    val newValue = ArrayList<MovieDto>()
                    if(!isUserInput) {
                        newValue.addAll(_data.value!!)
                    }
                    newValue.addAll(it.data)
                    _data.value = newValue
                }
            }
        }
    }

    fun loadNextPage(query: String){
        ++currentPage
        search(query, currentPage, false)
    }
}