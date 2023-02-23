package com.example.mouvie.ui.screen.favorite

import androidx.lifecycle.*
import com.example.mouvie.model.favorite.Favorite
import com.example.mouvie.repository.favorite.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel (private val repository: FavoriteRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allFavorites: LiveData<List<Favorite>> = repository.allFavorites.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(favorite: Favorite) = viewModelScope.launch {
        repository.insert(favorite)
    }
}

class FavoriteViewModelFactory(private val repository: FavoriteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}