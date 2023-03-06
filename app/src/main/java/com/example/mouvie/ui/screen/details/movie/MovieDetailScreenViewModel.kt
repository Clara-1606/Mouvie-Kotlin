package com.example.mouvie.ui.screen.details.movie

import android.util.Log
import androidx.lifecycle.*
import com.example.mouvie.MouvieApplication
import com.example.mouvie.config.fixed.UserPreferencesValues
import com.example.mouvie.config.state.DataState
import com.example.mouvie.dao.favorite.FavoriteDao
import com.example.mouvie.dao.favorite.FavoriteDao_Impl
import com.example.mouvie.model.favorite.Favorite
import com.example.mouvie.model.movie.dto.*
import com.example.mouvie.repository.favorite.FavoriteRepository
import com.example.mouvie.service.movie.MovieService
import com.example.mouvie.service.movie.WatchProvidersService
import com.example.mouvie.ui.screen.favorite.FavoriteViewModel
import kotlinx.coroutines.launch

class MovieDetailScreenViewModel(
    private val movieService: MovieService = MovieService(),
    private val watchProviderService: WatchProvidersService = WatchProvidersService(),
    private val repository: FavoriteRepository
): ViewModel() {

    // Actual movie details
    private val _data: MutableLiveData<MovieDetailDto> = MutableLiveData()
    val data: LiveData<MovieDetailDto> = _data

    // Current data state (loading, success or error)
    private val _dataState: MutableLiveData<DataState<MovieDetailDto>> = MutableLiveData()
    val dataState: LiveData<DataState<MovieDetailDto>> = _dataState

    // Similar movies data
    private val _similarMoviesData: MutableLiveData<List<MovieDto>> = MutableLiveData(mutableListOf())
    val similarMoviesData: LiveData<List<MovieDto>> = _similarMoviesData

    // Similar movies data state (loading, success or error)
    private val _similarMoviesDataState: MutableLiveData<DataState<List<MovieDto>>> = MutableLiveData()
    val similarMoviesDataState: LiveData<DataState<List<MovieDto>>> = _similarMoviesDataState

    // Recommended movies data
    private val _recommendedMoviesData: MutableLiveData<List<MovieDto>> = MutableLiveData(mutableListOf())
    val recommendedMoviesData: LiveData<List<MovieDto>> = _recommendedMoviesData

    // Recommended movies data state (loading, success or error)
    private val _recommendedMoviesDataState: MutableLiveData<DataState<List<MovieDto>>> = MutableLiveData()
    val recommendedMoviesDataState: LiveData<DataState<List<MovieDto>>> = _recommendedMoviesDataState

    // Watch providers data
    private val _watchProvidersData: MutableLiveData<WatchProviderCountryDto> = MutableLiveData()
    val watchProvidersData: LiveData<WatchProviderCountryDto> = _watchProvidersData

    // Watch providers data state (loading, success or error)
    private val _watchProvidersDataState: MutableLiveData<DataState<MovieWatchProvidersDto>> = MutableLiveData()
    val watchProvidersDataState: LiveData<DataState<MovieWatchProvidersDto>> = _watchProvidersDataState

    // Credits data
    private val _creditsData: MutableLiveData<MovieCreditsDto> = MutableLiveData()
    val creditsData: LiveData<MovieCreditsDto> = _creditsData

    // Credits data state (loading, success or error)
    private val _creditsDataState: MutableLiveData<DataState<MovieCreditsDto>> = MutableLiveData()
    val creditsDataState: LiveData<DataState<MovieCreditsDto>> = _creditsDataState

    val isFavorite: MutableLiveData<Boolean> = MutableLiveData()

    private var currentSimilarPage = 1
    private var currentRecommendedPage = 1

    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            movieService.getMovieDetails(movieId, UserPreferencesValues.LANGUAGE).collect {
                // Update the data state
                _dataState.value = it

                // Update actual data only when request succeeded
                if(it is DataState.Success) {
                    _data.value = it.data
                }
            }
        }
    }

    fun getSimilarMovies(movieId: Int, pageNumber: Int) {
        viewModelScope.launch {
            movieService.getSimilarMovies(movieId, pageNumber, UserPreferencesValues.LANGUAGE).collect {
                // Update the data state
                _similarMoviesDataState.value = it

                // Update actual data only when request succeeded
                if(it is DataState.Success) {
                    val newValue = ArrayList<MovieDto>()
                    newValue.addAll(_similarMoviesData.value!!)
                    newValue.addAll(it.data)
                    _similarMoviesData.value = newValue
                }
            }
        }
    }

    fun getRecommendedMovies(movieId: Int, pageNumber: Int) {
        viewModelScope.launch {
            movieService.getRecommendedMovies(movieId, pageNumber, UserPreferencesValues.LANGUAGE).collect {
                // Update the data state
                _recommendedMoviesDataState.value = it

                // Update actual data only when request succeeded
                if(it is DataState.Success) {
                    val newValue = ArrayList<MovieDto>()
                    newValue.addAll(_recommendedMoviesData.value!!)
                    newValue.addAll(it.data)
                    _recommendedMoviesData.value = newValue
                }
            }
        }
    }

    fun getWatchProviders(movieId: Int) {
        viewModelScope.launch {
            watchProviderService.getWatchProviders(movieId).collect {
                // Search for the desired language
                try {
                    _watchProvidersDataState.value = it

                    if(it is DataState.Success) {
                        // Using java reflection to search for the desired language
                        _watchProvidersData.value = it.data.results?.javaClass
                            ?.getMethod("get" + UserPreferencesValues.LANGUAGE.substring(3)) // To get property according the current user language (en-US) -> US
                            ?.invoke(it.data.results) as WatchProviderCountryDto
                    }

                } catch (e: java.lang.Exception){
                    Log.i("DEBUG", e.toString())
                }

            }
        }
    }

    fun getCredits(movieId: Int){
        viewModelScope.launch {
            movieService.getMovieCredits(movieId, UserPreferencesValues.LANGUAGE).collect {
                // Update the data state
                _creditsDataState.value = it

                // Update actual data only when request succeeded
                if(it is DataState.Success) {
                    // Order cast by the api order
                    _creditsData.value = it.data
                }
            }
        }
    }

    fun isFavorite(movieId: Int) = viewModelScope.launch {
        repository.get(movieId).collect{
            isFavorite.value = it != null
        }
    }

    fun addToFavorites(favorite: Favorite) = viewModelScope.launch {
        repository.insert(favorite)
        isFavorite(favorite.idMovie)
    }

    fun removeFromFavorites(movieId: Int) = viewModelScope.launch {
        repository.delete(movieId = movieId)
        isFavorite(movieId)
    }

    fun loadNextSimilarPage(movieId: Int){
        ++currentSimilarPage
        getSimilarMovies(movieId, currentSimilarPage)
    }

    fun loadNextRecommendedPage(movieId: Int){
        ++currentRecommendedPage
        getRecommendedMovies(movieId, currentRecommendedPage)
    }
}

class MovieDetailScreenViewModelFactory(private val repository: FavoriteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieDetailScreenViewModel(repository =  repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}