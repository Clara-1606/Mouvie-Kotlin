package com.example.mouvie.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mouvie.ui.*
import com.example.mouvie.ui.screen.favorite.FavoriteViewModel

@Composable
fun RootNavigationGraph(favoriteViewModel: FavoriteViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) { HomeScreen(navController, favoriteViewModel) }
        composable(Screens.Settings.route) { SettingsScreen(navController) }
    }
}