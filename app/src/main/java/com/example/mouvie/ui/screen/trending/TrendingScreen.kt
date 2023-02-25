package com.example.mouvie.ui.screen.trending

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.dto.MovieDto
import com.example.mouvie.model.movie.dto.MovieResponseDto
import com.example.mouvie.ui.widget.movie.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(
    navController: NavHostController,
    movieViewModel: PopularScreenViewModel = viewModel()
) {
    val data by movieViewModel.data.observeAsState()
    val dataState by movieViewModel.dataState.observeAsState()
    val scrollState = rememberLazyListState()

    Column {
        LazyColumn(
            state = scrollState) {
            items(items = data as List<MovieDto>) {
                    movie -> MovieCard(movie)
            }
        }

        if (dataState is DataState.Loading) {
            CircularProgressIndicator()
        } else if (dataState is DataState.Error) {
            Button(onClick = { movieViewModel.loadNextPage() }) {
                Text(text = "Load more")
            }
        }
    }


    // Load more data when bottom reached
    scrollState.OnBottomReached {
        movieViewModel.loadNextPage()
    }
}


@Composable
fun LazyListState.OnBottomReached(
    buffer : Int = 10,
    loadMore : () -> Unit
){
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore){
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}
