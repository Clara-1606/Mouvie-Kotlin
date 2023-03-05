package com.example.mouvie.ui.screen.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mouvie.config.state.DataState
import com.example.mouvie.model.movie.dto.MovieDto
import com.example.mouvie.ui.navigation.enums.Screens
import com.example.mouvie.ui.screen.trending.PopularScreenViewModel
import com.example.mouvie.ui.widget.movie.MovieCard
import com.example.mouvie.ui.widget.movie.SearchBar
import com.example.mouvie.ui.widget.movie.common.CenteredProgressIndicator
import com.example.mouvie.ui.widget.movie.common.OnBottomReached

@Composable
fun SearchScreen(
    navController: NavHostController = rememberNavController(),
    searchViewModel: SearchScreenViewModel = viewModel()
) {
    val data by searchViewModel.data.observeAsState()
    val dataState by searchViewModel.dataState.observeAsState()

    val scrollState = rememberLazyGridState()

    val textState = remember { mutableStateOf(TextFieldValue("")) }

    Column {
        SearchBar(textState
        ) { input ->
            searchViewModel.search(input, 1, true)
        }

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
                Button(onClick = { searchViewModel.loadNextPage(textState.value.text) }) {
                    Text(text = "Load more")
                }
            }
        }

        // Load more data when bottom reached
        scrollState.OnBottomReached {
            searchViewModel.loadNextPage(textState.value.text)
        }
    }
}