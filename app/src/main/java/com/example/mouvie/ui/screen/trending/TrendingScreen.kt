package com.example.mouvie.ui.screen.trending

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.MovieResponse
import com.example.mouvie.ui.widget.movie.MovieCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(
    navController: NavHostController,
    movieViewModel: PopularScreenViewModel = viewModel()
) {
    val movies by movieViewModel.dataState.observeAsState()
    val scrollState = rememberLazyListState()

    when(movies){
        is DataState.Success -> {
            LazyColumn(state = scrollState) {
                items(items = movies.data){
                    movie -> MovieCard(movie)
                }
            }
        }
        is DataState.Loading -> {
            CircularProgressIndicator()
        }
        else -> {
            Text(text = "An error occured")
        }
    }

    // Observer when reached end of list
    val endOfListReached by remember {
        derivedStateOf {
            scrollState.isScrolledToEnd()
        }
    }

    // Act when end of list reached
    LaunchedEffect(endOfListReached) {
        // Load next page
        movieViewModel.loadNextPage()
    }
}

fun LazyListState.isScrolledToEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
