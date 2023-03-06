package com.example.mouvie

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.compose.MouvieTheme
import com.example.mouvie.model.favorite.Favorite
import com.example.mouvie.ui.screen.favorite.FavoriteViewModel
import com.example.mouvie.ui.screen.favorite.FavoriteViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import com.example.mouvie.ui.navigation.graph.RootNavigationGraph
import com.example.mouvie.ui.screen.details.movie.MovieDetailScreenViewModel
import com.example.mouvie.ui.screen.details.movie.MovieDetailScreenViewModelFactory


class MainActivity : ComponentActivity() {
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory((application as MouvieApplication).repository)
    }
    private val movieDetailViewModel: MovieDetailScreenViewModel by viewModels {
        MovieDetailScreenViewModelFactory((application as MouvieApplication).repository)
    }

    lateinit var allFavorite : Flow<List<Favorite>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = (application as MouvieApplication).database.favoriteDao()
        lifecycleScope.launch {
            allFavorite = dao.getFavorites()
            allFavorite.collect { favorites ->
                for (favorite in favorites) {
                    Log.i(MainActivity::class.java.simpleName, favorite.name + " " + favorite.idMovie)
                }
            }
        }
        setContent {
            MouvieTheme {
                RootNavigationGraph(favoriteViewModel, movieDetailViewModel)
            }
        }
    }
}