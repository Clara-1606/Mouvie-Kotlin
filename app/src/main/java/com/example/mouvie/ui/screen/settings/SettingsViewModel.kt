package com.example.mouvie.ui.screen.settings

import android.content.Context
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.*
import com.example.mouvie.model.preferences.UserPreferences
import com.example.mouvie.repository.UserPreferencesRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val context: Context,
    private val userPreferencesRepository: UserPreferencesRepository = UserPreferencesRepository(context)
) : ViewModel() {
    companion object Languages {
        val FRENCH = "fr-FR"
        val ENGLISH = "en-US"
    }

    val userPreference: MutableLiveData<UserPreferences> = MutableLiveData(UserPreferences(Locale.current.toLanguageTag()))

    init{
        getUserPreferences()
    }

    fun getUserPreferences() {
        viewModelScope.launch {
            userPreferencesRepository.userPreferencesFlow.collect{
                userPreference.value = it
            }
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateLanguage(language)
            getUserPreferences()
        }
    }
}