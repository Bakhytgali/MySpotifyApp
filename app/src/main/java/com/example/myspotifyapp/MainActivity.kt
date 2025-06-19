package com.example.myspotifyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.myspotifyapp.ui.theme.MySpotifyAppTheme
import com.example.myspotifyapp.ui.theme.screens.Login
import com.example.myspotifyapp.viewModels.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val themeViewModel = ThemeViewModel()

        setContent {
            MySpotifyAppTheme(darkTheme = themeViewModel.isDarkMode) {
                Login()
            }
        }
    }
}