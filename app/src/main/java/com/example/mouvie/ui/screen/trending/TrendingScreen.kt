package com.example.mouvie.ui.screen.trending

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.dto.MovieDto
import com.example.mouvie.ui.navigation.enums.Screens
import com.example.mouvie.ui.widget.movie.MovieCard
import com.example.mouvie.ui.widget.movie.common.CenteredProgressIndicator
import com.example.mouvie.ui.widget.movie.common.OnBottomReached

@Composable
fun TrendingScreen(
    navController: NavHostController,
    movieViewModel: PopularScreenViewModel = viewModel()
) {
    val data by movieViewModel.data.observeAsState()
    val dataState by movieViewModel.dataState.observeAsState()
    val scrollState = rememberLazyGridState()

    Column (horizontalAlignment = Alignment.CenterHorizontally) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = scrollState,
            ) {
            items(items = data as List<MovieDto>) {
                movie -> MovieCard(movie, onClick = {
                    val navRoute =  Screens.MovieDetail.route.replace(
                        oldValue =  "{" + Screens.MovieDetail.pathArg + "}",
                        newValue =  movie.id.toString()
                    )
                    navController.navigate(navRoute)
                }
            )
            }
        }

        if (dataState is DataState.Loading) {
            // Printing a progress indicator at the list's end whenever data is loading
            CenteredProgressIndicator()

        } else if (dataState is DataState.Error) {
            // On error the user can try to load more data manually
            Button(onClick = { movieViewModel.loadNextPage() }) {
                // TODO : Decrease the page value before reloading and add translation
                Text(text = "Load more")
            }
        }
    }

    // Load more data when bottom reached
    scrollState.OnBottomReached {
        movieViewModel.loadNextPage()
    }
}
