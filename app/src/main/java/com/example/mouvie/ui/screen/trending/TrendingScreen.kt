package com.example.mouvie.ui.screen.trending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import com.example.mouvie.ui.widget.movie.MovieCard

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
                    movie -> MovieCard(movie)
            }
        }

        if (dataState is DataState.Loading) {
            // Printing a progress indicator at the list's end whenever data is loading
            // TODO : Center this indicator
            CircularProgressIndicator()

        } else if (dataState is DataState.Error) {
            // On error the user can try to load more data manually
            Button(onClick = { movieViewModel.loadNextPage() }) {
                // TODO : Decrease the page value before reloading
                Text(text = "Load more")
            }
        }
    }

    // Used to make the images load earlier
//    GlideLazyListPreloader(
//        state = scrollState,
//        data = data as List<MovieDto>,
//        size = Size(500F,500F),
//        numberOfItemsToPreload = 15,
//        fixedVisibleItemCount = 3,
//    ) { item, requestBuilder ->
//        requestBuilder.load("https://image.tmdb.org/t/p/w500" + item.poster_path)
//    }

    // Load more data when bottom reached
    scrollState.OnBottomReached {
        movieViewModel.loadNextPage()
    }
}


@Composable
fun LazyGridState.OnBottomReached(
    buffer : Int = 15,
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
