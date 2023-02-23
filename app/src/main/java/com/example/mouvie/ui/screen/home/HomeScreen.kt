package com.example.mouvie.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mouvie.R
import com.example.mouvie.ui.navigation.BottomNavigationScreens
import com.example.mouvie.ui.navigation.Screens
import com.example.mouvie.ui.screen.favorite.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    rootNavController: NavController,
    favoriteViewModel: FavoriteViewModel,
    navController: NavHostController = rememberNavController()
) {
    var selectedItem by remember { mutableStateOf(1) }

    val navItems = BottomNavigationScreens.values()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { rootNavController.navigate(Screens.Settings.route) }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(Screens.Settings.title)
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth()
            ) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = stringResource(item.title)) },
                        label = { Text(stringResource(item.title)) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(item.route)
                        }
                    )
                }
            }
        },
        content = { innerPadding ->
            NavHost(navController, startDestination = BottomNavigationScreens.Trending.route, Modifier.padding(innerPadding)) {
                composable(BottomNavigationScreens.Trending.route) { TrendingScreen(navController) }
                composable(BottomNavigationScreens.Favorite.route) { FavoriteScreen(navController, favoriteViewModel) }
                composable(BottomNavigationScreens.Search.route) { SearchScreen(navController) }
            }
        }
    )
}