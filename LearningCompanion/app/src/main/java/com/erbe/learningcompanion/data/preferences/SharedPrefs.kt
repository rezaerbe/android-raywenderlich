package com.erbe.learningcompanion.data.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

private const val KEY_DARK_THEME = "dark_theme_enabled"

class SharedPrefs @Inject constructor(private val preferences: SharedPreferences) {

    fun setDarkThemeEnabled(enabled: Boolean) {
        preferences.edit { putBoolean(KEY_DARK_THEME, enabled) }
    }

    fun isDarkThemeEnabled() = preferences.getBoolean(KEY_DARK_THEME, false)
}