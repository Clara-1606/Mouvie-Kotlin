package com.example.mouvie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mouvie.ui.composant.MovieCard
import com.example.mouvie.ui.screen.favorite.FavoriteViewModel
import com.example.mouvie.model.favorite.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LiveData

@Composable
fun FavoriteScreen(
    navController: NavHostController = rememberNavController(), favoriteViewModel: FavoriteViewModel
) {
    Column() {
        Text(text = "Favorites Screen")
    }
    val allFavorites: LiveData<List<Favorite>> = favoriteViewModel.allFavorites

    var allFavoritesState by remember { mutableStateOf(emptyList<Favorite>()) }

    LaunchedEffect(allFavorites) {
        allFavorites.observeForever { favorites ->
            allFavoritesState = favorites
        }
    }

    LazyColumn {
        items(allFavoritesState) { favorite ->
            MovieCard(movie = favorite)
        }
    }
}


