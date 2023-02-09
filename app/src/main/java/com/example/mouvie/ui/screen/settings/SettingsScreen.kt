package com.example.mouvie.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mouvie.ui.navigation.Screens


@Composable
fun SettingsScreen(
    navController: NavHostController
) {
    Column {
        Row {
            IconButton(onClick = { navController.navigate(Screens.Home.route) }) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(Screens.Home.title),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Row {
            Text(
                modifier = Modifier.padding(horizontal = 40.dp),
                text = stringResource(Screens.Settings.title),
                style = MaterialTheme.typography.displaySmall,
            )
        }

    }
}