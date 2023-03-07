package com.example.mouvie.ui.screen.settings

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import com.example.mouvie.R
import com.example.mouvie.ui.navigation.enums.Screens
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    settingsViewModel: SettingsViewModel = SettingsViewModel(LocalContext.current)
) {

    val data by settingsViewModel.userPreference.observeAsState()

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
        Row {
            Column(Modifier.selectableGroup()) {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = stringResource(R.string.language),
                    style = MaterialTheme.typography.headlineSmall,
                )
                Row(Modifier.padding(horizontal = 30.dp)) {
                    RadioButton(
                        selected = (data?.language
                            ?: false) == SettingsViewModel.FRENCH,
                        onClick = {
                        settingsViewModel.updateLanguage(SettingsViewModel.FRENCH)
                    }, Modifier.align(CenterVertically))
                    Text(text = stringResource(R.string.french), Modifier.align(CenterVertically))
                }
                Row(Modifier.padding(horizontal = 30.dp)) {
                    RadioButton(
                        selected = (data?.language
                        ?: false) == SettingsViewModel.ENGLISH,
                        onClick = {
                        settingsViewModel.updateLanguage(SettingsViewModel.ENGLISH)
                    }, Modifier.align(CenterVertically))
                    Text(text = stringResource(R.string.english), Modifier.align(CenterVertically))
                }

            }
        }

    }
}