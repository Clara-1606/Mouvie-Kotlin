package com.example.mouvie.ui.screen.details.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mouvie.config.fixed.ApiValues.Companion.IMAGE_ORIGINAL_URL
import com.example.mouvie.config.state.DataState
import com.example.mouvie.ui.widget.movie.HorizontalMovieList
import com.example.mouvie.ui.widget.movie.common.CenteredProgressIndicator
import com.example.mouvie.ui.widget.movie.common.ChipHorizontalList

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    navController: NavHostController,
    movieId: Int,
    movieDetailScreenViewModel: MovieDetailScreenViewModel = viewModel()
) {
    // Movie details
    val moviesData by movieDetailScreenViewModel.data.observeAsState()
    val moviesDataState by movieDetailScreenViewModel.dataState.observeAsState()

    // Similar movies
    val similarMoviesData by movieDetailScreenViewModel.similarMoviesData.observeAsState()
    val similarMoviesDataState by movieDetailScreenViewModel.similarMoviesDataState.observeAsState()

    // Recommended movies
    val recommendedMoviesData by movieDetailScreenViewModel.recommendedMoviesData.observeAsState()
    val recommendedMoviesDataState by movieDetailScreenViewModel.recommendedMoviesDataState.observeAsState()

    LaunchedEffect(Unit){
        // Initial data load
        movieDetailScreenViewModel.getMovieDetails(movieId)
        movieDetailScreenViewModel.getSimilarMovies(movieId, 1)
        movieDetailScreenViewModel.getRecommendedMovies(movieId, 1)
    }

    moviesData?.let { movie ->
        LazyColumn(content = {
            item {
                // Backdrop
                // TODO load after image loaded
                // TODO Placeholder image
                GlideImage(
                    model = IMAGE_ORIGINAL_URL + movie.backdrop_path,
                    contentDescription = movie.title)
            }
            item {
                // Title
                Text(text = movie.title, style = MaterialTheme.typography.displaySmall)
            }
            item {
                // Genres chips
                ChipHorizontalList(content = movie.genres.map { it.name })
            }
            item {
                // TODO : Streaming services
            }
            item {
                // Description
                // TODO : Padding
                movie.overview?.let {
                    Column() {
                        Text(text = "Overview", style = MaterialTheme.typography.titleLarge)
                        Text(text = it, style = MaterialTheme.typography.bodyMedium)
                    }
                }

            }
            item {
                // Recommended movies ?
                HorizontalMovieList(
                    title = "Recommended movies",
                    oneEndReached = { movieDetailScreenViewModel.loadNextRecommendedPage(movieId) },
                    data = recommendedMoviesData,
                    dataState = recommendedMoviesDataState,
                    navController = navController
                )
            }
            item {
                // Similar movies ?
                HorizontalMovieList(
                    title = "Similar movies",
                    oneEndReached = { movieDetailScreenViewModel.loadNextSimilarPage(movieId) },
                    data = similarMoviesData,
                    dataState = similarMoviesDataState,
                    navController = navController
                )
            }
        })

    }

    if (moviesDataState is DataState.Loading) {
        // Printing a progress indicator while data is loading
        CenteredProgressIndicator()

    } else if (moviesDataState is DataState.Error) {
        // TODO : handle errors : Create a common widget / class / helper
    }
}