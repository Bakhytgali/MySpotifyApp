package com.example.myspotifyapp.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ThemeViewModel: ViewModel() {
    var isDarkMode by mutableStateOf(false)
        private set

    fun toggleTheming() {
        isDarkMode = !isDarkMode
    }
}