package com.example.mouvie.ui.screen.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mouvie.model.favorite.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.asFlow
import com.example.mouvie.R
import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.dto.MovieDto
import com.example.mouvie.ui.composant.MovieGrid
import com.example.mouvie.ui.navigation.enums.Screens
import com.example.mouvie.ui.widget.movie.MovieCard
import com.example.mouvie.ui.widget.movie.common.CenteredProgressIndicator
import kotlinx.coroutines.flow.Flow

@Composable
fun FavoriteScreen(
    navController: NavHostController = rememberNavController(),
    favoriteViewModel: FavoriteViewModel
) {
    var allFavoritesState by remember { mutableStateOf(emptyList<Favorite>()) }
    val allFavorites: Flow<List<Favorite>> = favoriteViewModel.allFavorites.asFlow()

    LaunchedEffect(allFavorites) {
        allFavorites.collect { favorites ->
            allFavoritesState = favorites
        }
    }

    val scrollState = rememberLazyGridState()

    if (allFavoritesState.isNotEmpty()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = scrollState,
            ) {
                items(items = allFavoritesState) { movie ->
                    MovieCard(
                        MovieDto(poster_path = movie.posterPath, id = movie.idMovie, title = "", adult = false, backdrop_path = "", genre_ids = listOf(), original_title = "", original_language = "", overview = "", popularity = 0, release_date = "", vote_average = 0, vote_count = 0)
                        , onClick = {
                        val navRoute = Screens.MovieDetail.route.replace(
                            oldValue = "{" + Screens.MovieDetail.pathArg + "}",
                            newValue = movie.idMovie.toString()
                        )
                        navController.navigate(navRoute)
                    }
                    )
                }
            }
        }
    }
    else {
        Text(stringResource(R.string.no_movies))
    }
}


