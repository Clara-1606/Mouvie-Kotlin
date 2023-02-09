package com.example.mouvie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun FavoriteScreen(
    navController: NavHostController = rememberNavController()
) {
    Column() {
        Text(text = "Favorites Screen")
    }
}