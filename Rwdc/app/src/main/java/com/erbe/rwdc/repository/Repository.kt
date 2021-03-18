package com.erbe.rwdc.repository

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData

interface Repository : LifecycleObserver {
    fun getPhotos(): LiveData<List<String>>
    fun getBanner(): LiveData<String>
    fun registerLifecycle(lifecycle: Lifecycle)
}