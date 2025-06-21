package com.example.myspotifyapp

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myspotifyapp.data.model.NavOptions
import com.example.myspotifyapp.ui.theme.screens.Login
import com.example.myspotifyapp.ui.theme.screens.Main
import com.example.myspotifyapp.utils.SpotifyAuthManager
import com.example.myspotifyapp.utils.TokenManager
import com.example.myspotifyapp.viewModels.AuthViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    context: Context,
    onClickLogin: () -> Unit,
) {
    val startDestination = if (TokenManager.getAccessToken(context) == null) {
        NavOptions.LOGIN.screen
    } else {
        NavOptions.MAIN.screen
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavOptions.LOGIN.screen) {
            Login(onClickLogin)
        }

        composable(NavOptions.MAIN.screen) {
            Main()
        }
    }
}