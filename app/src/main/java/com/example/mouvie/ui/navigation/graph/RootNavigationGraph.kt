package com.example.mouvie.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mouvie.ui.*
import com.example.mouvie.ui.navigation.enums.Screens

@Composable
fun RootNavigationGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) { HomeScreen(navController) }
        composable(Screens.Settings.route) { SettingsScreen(navController) }
    }
}