package com.example.myspotifyapp.viewModels

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.example.myspotifyapp.utils.TokenManager
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.NoClientAuthentication

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    fun handleAuth(
        intent: Intent,
        activity: Activity,
        authorizationRequest: AuthorizationRequest,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            val uri = intent.data

            if (uri == null) {
                onError("URI is null")
                return
            }

            val code = uri.getQueryParameter("code")
            val state = uri.getQueryParameter("state")

            if (code == null || state == null) {
                onError("❌ Missing code or state in URI")
                return
            }

            val response = AuthorizationResponse.Builder(authorizationRequest)
                .setAuthorizationCode(code)
                .setState(state)
                .build()

            val tokenRequest = response.createTokenExchangeRequest()
            val authService = AuthorizationService(activity)

            authService.performTokenRequest(
                tokenRequest,
                NoClientAuthentication.INSTANCE
            ) { tokenResponse, exception ->
                if (exception != null) {
                    onError("❌ Token exchange failed: ${exception.errorDescription}")
                    return@performTokenRequest
                }

                val token = tokenResponse?.accessToken

                if (token != null) {
                    TokenManager.saveTokens(
                        context = getApplication(),
                        accessToken = tokenResponse.accessToken!!,
                        refreshToken = tokenResponse.refreshToken,
                        expiresAt = tokenResponse.accessTokenExpirationTime
                    )
                    onSuccess(token)
                } else {
                    onError("❌ Access token was null")
                }
            }
        } catch (e: Exception) {
            onError("❌ Exception: ${e.message}")
        }
    }
}
