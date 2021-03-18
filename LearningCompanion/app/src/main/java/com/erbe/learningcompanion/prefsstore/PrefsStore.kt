package com.erbe.learningcompanion.prefsstore

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    fun isNightMode(): Flow<Boolean>

    suspend fun toggleNightMode()
}