package com.example.mouvie.config.fixed

import androidx.compose.ui.text.intl.Locale

 class UserPreferencesValues {
     companion object {
         val LANGUAGE: String = Locale.current.toLanguageTag()
     }
 }