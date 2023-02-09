package com.example.mouvie.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mouvie.R

/**
 * Enum values that represent the screens in the app located inside the bottom navigation menu
 */
enum class BottomNavigationScreens(
    @StringRes  val title: Int,
                val route: String,
                val icon: ImageVector,
) {
    Search(title = R.string.search_screen_title, route = "search", icon = Icons.Rounded.Search),
    Trending(title = R.string.trending_screen_title, route = "trending", icon = Icons.Rounded.Home),
    Favorite(title = R.string.favorite_screen_title, route = "favorite", icon = Icons.Rounded.Favorite),
}


