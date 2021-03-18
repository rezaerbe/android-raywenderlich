package com.erbe.learningcompanion.prefsstore

import android.content.Context
import androidx.datastore.preferences.*
import com.erbe.learningcompanion.di.PREFS_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val STORE_NAME = "learning_data_store"

class PrefsStoreImpl @Inject constructor(
    @ApplicationContext context: Context
) : PrefsStore {

    private val dataStore = context.createDataStore(
        name = STORE_NAME,
        migrations = listOf(SharedPreferencesMigration(context, PREFS_NAME))
    )

    override fun isNightMode() = dataStore.data.catch { exception ->
        // dataStore.data throws an IOException when an error is encountered when reading data
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { it[PreferencesKeys.NIGHT_MODE_KEY] ?: false }

    override suspend fun toggleNightMode() {
        dataStore.edit {
            it[PreferencesKeys.NIGHT_MODE_KEY] = !(it[PreferencesKeys.NIGHT_MODE_KEY] ?: false)
        }
    }

    private object PreferencesKeys {
        val NIGHT_MODE_KEY = preferencesKey<Boolean>("dark_theme_enabled")
    }
}