package com.example.mouvie.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mouvie.ui.screen.favorite.FavoriteViewModel
import com.example.mouvie.model.favorite.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.lifecycle.asFlow
import com.example.mouvie.R
import com.example.mouvie.ui.composant.MovieGrid
import kotlinx.coroutines.flow.Flow

@Composable
fun FavoriteScreen(
    navController: NavHostController = rememberNavController(), favoriteViewModel: FavoriteViewModel
) {
    //For the tests, we add to the database of films, when we have the api, we will no longer need that
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

    // Function for delete
    val onDelete: (Favorite) -> Unit = { movie ->
            favoriteViewModel.delete(movie)
    }

    if (allFavoritesState.isNotEmpty()) {
        MovieGrid(movies = allFavoritesState, Modifier.padding(horizontal = 8.dp), onDelete = onDelete)

    } else {
        Text(stringResource(R.string.no_movies))
    }
}


