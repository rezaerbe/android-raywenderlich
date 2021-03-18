package com.erbe.filevoid.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class FileUpdateBroadcastReceiver(
        val path: String,
        private val onChange: () -> Unit) : BroadcastReceiver() {

    companion object {
        const val EXTRA_PATH = "com.erbe.filevoid.fileservice.path"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val filePath = intent?.extras?.getString(EXTRA_PATH)
        if (filePath.equals(path)) {
            onChange.invoke()
        }
    }
}