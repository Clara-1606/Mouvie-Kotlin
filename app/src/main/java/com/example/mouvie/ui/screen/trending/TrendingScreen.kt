package com.example.mouvie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun TrendingScreen(
    navController: NavHostController = rememberNavController()
) {
    Column() {
        Text(text = "Trending Screen")
    }
}