package com.example.myspotifyapp.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.math.exp

object TokenManager {
    private const val SHARED_PREFS_KEY = "prefs"
    private const val ACCESS_TOKEN_KEY = "access_token"
    private const val REFRESH_TOKEN_KEY = "refresh_token"
    private const val EXPIRES_AT_KEY = "expires_at"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE)
    }

    fun saveTokens(
        context: Context,
        accessToken: String,
        refreshToken: String?,
        expiresAt: Long?
    ) {
        with(getPrefs(context).edit()) {
            putString(ACCESS_TOKEN_KEY, accessToken)
            putString(REFRESH_TOKEN_KEY, refreshToken)
            putLong(EXPIRES_AT_KEY, expiresAt ?: 0)
            apply()
        }
    }

    fun getAccessToken(context: Context): String? = getPrefs(context).getString(ACCESS_TOKEN_KEY, null)
    fun getRefreshToken(context: Context): String? = getPrefs(context).getString(REFRESH_TOKEN_KEY, null)
    private fun getExpiresAt(context: Context): Long = getPrefs(context).getLong(EXPIRES_AT_KEY, 0)

    fun clearTokens(context: Context) {
        getPrefs(context).edit().clear().apply()
    }

    fun isTokenExpired(context: Context): Boolean {
        val expiry = getExpiresAt(context)
        return System.currentTimeMillis() >= expiry
    }
}