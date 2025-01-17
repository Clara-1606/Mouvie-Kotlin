package com.example.mouvie.ui.screen.details.movie

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mouvie.R
import com.example.mouvie.config.fixed.ApiValues.Companion.IMAGE_ORIGINAL_URL
import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.favorite.Favorite
import com.example.mouvie.model.movie.entity.PersonEntity
import com.example.mouvie.ui.widget.movie.HorizontalMovieList
import com.example.mouvie.ui.widget.movie.HorizontalPersonList
import com.example.mouvie.ui.widget.movie.WatchProvidersListWidget
import com.example.mouvie.ui.widget.movie.common.CenteredProgressIndicator
import com.example.mouvie.ui.widget.movie.common.ChipHorizontalList


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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

    // Streaming services
    val watchProvidersData by movieDetailScreenViewModel.watchProvidersData.observeAsState()
    val watchProvidersDataState by movieDetailScreenViewModel.watchProvidersDataState.observeAsState()

    // Credits
    val creditsData by movieDetailScreenViewModel.creditsData.observeAsState()
    val creditsDataState by movieDetailScreenViewModel.creditsDataState.observeAsState()

    // Favorite
    val isFavorite by movieDetailScreenViewModel.isFavorite.observeAsState()

    LaunchedEffect(Unit){
        // Initial data load
        movieDetailScreenViewModel.getMovieDetails(movieId)
        movieDetailScreenViewModel.getSimilarMovies(movieId, 1)
        movieDetailScreenViewModel.getRecommendedMovies(movieId, 1)
        movieDetailScreenViewModel.getWatchProviders(movieId)
        movieDetailScreenViewModel.getCredits(movieId)
        movieDetailScreenViewModel.isFavorite(movieId)
    }
Scaffold(
    topBar = {
        SmallTopAppBar(
            title = { Text(text = "") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Outlined.ArrowBack, "", tint = Color.White)
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color(0, 0, 0, 50)),
        )
    },
) {
    moviesData?.let { movie ->
        LazyColumn( content = {
            item {
                // Backdrop : Maybe Replace by video trailer ?
                // TODO load after image loaded -> See glide documentation and scroll top
                // TODO Placeholder image
                GlideImage(
                    model = IMAGE_ORIGINAL_URL + movie.backdrop_path,
                    contentDescription = movie.title
                )
            }
            item {
                // Title
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    IconButton(
                        onClick = {
                            if (isFavorite!!) {
                                movieDetailScreenViewModel.removeFromFavorites(movieId = movieId)
                            } else {
                                movieDetailScreenViewModel.addToFavorites(Favorite(idMovie = movie.id, name = movie.title, posterPath = movie.poster_path!!, id = 0))
                            }
                        },
                    ) {
                        if (isFavorite!!) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "Favorite",
                            )
                        } else {
                            Icon(
                                Icons.Outlined.FavoriteBorder,
                                contentDescription = "Not Favorite")
                        }

                    }
                }

            }
            item {
                // Title
                Text(text = movie.title, style = MaterialTheme.typography.displaySmall, modifier = Modifier.padding(10.dp))
            }
            item {
                // Genres chips
                ChipHorizontalList(content = movie.genres.map { it.name })
            }
            item {
                // Description
                movie.overview?.let {
                    Column {
                        Text(text = stringResource(R.string.overview), style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(10.dp))
                        Text(text = it, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(15.dp))
                    }
                }
            }
            item {
                watchProvidersData?.let {
                    if (it.flatrate != null || it.buy != null || it.rent != null) {
                        Column {
                            Text(text = stringResource(R.string.streming_category), style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(10.dp))
                            it.flatrate?.let { flatrate ->
                                WatchProvidersListWidget(stringResource(R.string.stream), flatrate)
                            }
                            it.buy?.let { buy ->
                                WatchProvidersListWidget(stringResource(R.string.buy), buy)
                            }
                            it.rent?.let { rent ->
                                WatchProvidersListWidget(stringResource(R.string.rent), rent)
                            }

                        }
                    }
                }
            }
            item {
                creditsData?.cast?.let {
                    HorizontalPersonList(title = stringResource(R.string.cast), persons = it.map { castMember -> PersonEntity(castMember.name, castMember.profile_path) })
                }
            }
            item {
                // Recommended movies
                HorizontalMovieList(
                    title = stringResource(R.string.recommended_movies),
                    oneEndReached = { movieDetailScreenViewModel.loadNextRecommendedPage(movieId) },
                    data = recommendedMoviesData,
                    dataState = recommendedMoviesDataState,
                    navController = navController
                )
            }
            item {
                // Similar movies
                HorizontalMovieList(
                    title = stringResource(R.string.similar_movies),
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

}