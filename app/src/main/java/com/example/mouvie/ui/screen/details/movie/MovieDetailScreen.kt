package com.example.mouvie.ui.screen.details.movie

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mouvie.config.state.DataState
import com.example.mouvie.ui.widget.movie.common.CenteredProgressIndicator

@Composable
fun MovieDetailScreen(
    navController: NavHostController,
    movieId: Int,
    movieDetailScreenViewModel: MovieDetailScreenViewModel = viewModel()
) {
    val data by movieDetailScreenViewModel.data.observeAsState()
    val dataState by movieDetailScreenViewModel.dataState.observeAsState()

    LaunchedEffect(Unit){
        movieDetailScreenViewModel.getMovieDetails(movieId)
    }

    

    data?.let { Text(text = it.title) }

    if (dataState is DataState.Loading) {
        // Printing a progress indicator while data is loading
        CenteredProgressIndicator()

    } else if (dataState is DataState.Error) {
        // TODO : handle errors
    }
}