package com.example.mouvie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.example.mouvie.ui.composant.MovieGrid
import kotlinx.coroutines.flow.Flow

@Composable
fun FavoriteScreen(
    navController: NavHostController = rememberNavController(), favoriteViewModel: FavoriteViewModel
) {
    val favorite = Favorite(1, 1, "Test", "https://imgr.cineserie.com/2022/06/2168663-2.jpg?imgeng=/f_jpg/cmpr_0/w_864/h_1080/m_cropbox&ver=1")
    favoriteViewModel.insert(favorite)
    val favorite2 = Favorite(2, 2, "Smile", "https://imgr.cineserie.com/2022/06/2168663-2.jpg?imgeng=/f_jpg/cmpr_0/w_864/h_1080/m_cropbox&ver=1")
    favoriteViewModel.insert(favorite2)
    val favorite3 = Favorite(3, 3, "Astérix", "https://www.ugc.fr/dynamique/films/49/14949/fr/poster/large/0336388_18.jpg")
    favoriteViewModel.insert(favorite3)
    val allFavorites: Flow<List<Favorite>> = favoriteViewModel.allFavorites.asFlow()

    var allFavoritesState by remember { mutableStateOf(emptyList<Favorite>()) }

    LaunchedEffect(allFavorites) {
        allFavorites.collect { favorites ->
            allFavoritesState = favorites
        }
    }

    if (allFavoritesState.isNotEmpty()) {
        MovieGrid(movies = allFavoritesState, Modifier.padding(horizontal = 8.dp) )

    } else {
        Text("Aucun film en favoris")
    }
}


