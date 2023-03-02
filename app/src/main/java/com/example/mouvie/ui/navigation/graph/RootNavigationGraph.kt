package com.example.mouvie.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mouvie.ui.*
import com.example.mouvie.ui.navigation.enums.Screens
import com.example.mouvie.ui.screen.details.movie.MovieDetailScreen

@Composable
fun RootNavigationGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) { HomeScreen(navController) }
        composable(Screens.Settings.route) { SettingsScreen(navController) }
        composable(Screens.MovieDetail.route) { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getString(Screens.MovieDetail.pathArg)
            movieId?.let {
                MovieDetailScreen(navController, it.toInt())
            }
        }
    }
}