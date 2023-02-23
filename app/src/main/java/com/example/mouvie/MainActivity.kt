package com.example.mouvie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.compose.MouvieTheme
import com.example.mouvie.MouvieApplication
import com.example.mouvie.ui.navigation.RootNavigationGraph
import com.example.mouvie.ui.screen.favorite.FavoriteViewModel
import com.example.mouvie.ui.screen.favorite.FavoriteViewModelFactory


class MainActivity : AppCompatActivity() {
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory((application as MouvieApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MouvieTheme {
                RootNavigationGraph(favoriteViewModel)
            }
        }
    }
}