package com.example.myspotifyapp.utils

import android.app.Activity
import android.net.Uri
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues

class SpotifyAuthManager(private val activity: Activity) {

    private val authService = AuthorizationService(activity)

    private val serviceConfig = AuthorizationServiceConfiguration(
        Uri.parse("https://accounts.spotify.com/authorize"),
        Uri.parse("https://accounts.spotify.com/api/token")
    )

    private val clientId = "6c0af5c862d8474fa8c14652fe4a52c3"
    private val redirectUri = Uri.parse("myspotifyapp://callback")

    var lastAuthRequest: AuthorizationRequest? = null
        private set

    fun startAuth(activity: Activity) {
        val authRequest = AuthorizationRequest.Builder(
            serviceConfig,
            clientId,
            ResponseTypeValues.CODE,
            redirectUri
        ).setScopes("user-read-email", "user-read-private").build()

        lastAuthRequest = authRequest

        val authIntent = authService.getAuthorizationRequestIntent(authRequest)
        activity.startActivity(authIntent)
    }

    fun dispose() {
        authService.dispose()
    }
}
