package com.example.myspotifyapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import com.example.myspotifyapp.ui.theme.MySpotifyAppTheme
import com.example.myspotifyapp.ui.theme.screens.Login
import com.example.myspotifyapp.utils.SpotifyAuthManager
import com.example.myspotifyapp.viewModels.AuthViewModel
import com.example.myspotifyapp.viewModels.ThemeViewModel

class MainActivity : ComponentActivity() {
    private lateinit var spotifyAuthManager: SpotifyAuthManager
    private val themeViewModel = ThemeViewModel()
    private val authViewModel = AuthViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        spotifyAuthManager = SpotifyAuthManager(this)
        setContent {
            MySpotifyAppTheme(darkTheme = themeViewModel.isDarkMode) {
                Login {
                    spotifyAuthManager.startAuth(this)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        Log.d("Spotify Auth", "🔥 onNewIntent was called successfully!")
        Log.d("Spotify Auth", "🔥 Received: ${intent.data}")

        intent.let {
            authViewModel.handleAuth(
                intent = it,
                activity = this,
                authorizationRequest = spotifyAuthManager.lastAuthRequest!!,
                onSuccess = { token ->
                    Log.d("Spotify Auth", "✅ Success! TOKEN: $token")
                },
                onError = { error ->
                    Log.e("Spotify Auth", "❌ Error: $error")
                }
            )
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        spotifyAuthManager.dispose()
    }
}