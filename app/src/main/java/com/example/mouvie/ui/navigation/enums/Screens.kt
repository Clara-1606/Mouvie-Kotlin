package com.example.mouvie.ui.navigation.enums

import androidx.annotation.StringRes
import com.example.mouvie.R

/**
* Enum values that represent all the app screens
*/
enum class Screens(
    @StringRes val title: Int,
    val route: String
) {
    Home      (title = R.string.app_name,  route = "home"),
    Settings    (title = R.string.settings_screen_title, route = "settings"),
}