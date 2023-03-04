package com.example.mouvie.ui.widget.movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.dto.MovieDto
import com.example.mouvie.ui.navigation.enums.Screens
import com.example.mouvie.ui.widget.movie.common.CenteredProgressIndicator
import com.example.mouvie.ui.widget.movie.common.OnBottomReached

@Composable
fun HorizontalMovieList(
    title : String,
    oneEndReached : () -> Unit,
    data: List<MovieDto>?,
    dataState: DataState<List<MovieDto>>?,
    navController: NavHostController
    ) {

    val scrollState = rememberLazyListState()

    if ((data as List<MovieDto>).isNotEmpty()) {
        Column() {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            LazyRow(
                Modifier.align(CenterHorizontally),
                state = scrollState
            ) {
                items(data as List<MovieDto>) { movie ->
                    MovieCard(movie, onClick = {
                        val navRoute = Screens.MovieDetail.route.replace(
                            oldValue = "{" + Screens.MovieDetail.pathArg + "}",
                            newValue = movie.id.toString()
                        )
                        navController.navigate(navRoute) {
                            // Navigate and replace the current screen
                            popUpTo(Screens.Home.route)
                        }
                    })
                }
            }
        }
    }


    if (dataState is DataState.Loading) {
        // Printing a progress indicator while data is loading
        CenteredProgressIndicator()

    } else if (dataState is DataState.Error) {
        // TODO : handle errors : Create a common widget / class / helper
    }

    // Load more data when bottom reached
    scrollState.OnBottomReached {
        oneEndReached()
    }
}