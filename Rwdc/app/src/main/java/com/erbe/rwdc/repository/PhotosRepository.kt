package com.erbe.rwdc.repository

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.erbe.rwdc.app.PhotosUtils
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PhotosRepository : Repository, CoroutineScope {

    private val photosLiveData = MutableLiveData<List<String>>()
    private val bannerLiveData = MutableLiveData<String>()
    private val TAG = PhotosRepository::class.java.simpleName
    private val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun getPhotos(): LiveData<List<String>> {
        launch {
            fetchPhotos()
        }
        return photosLiveData
    }

    override fun getBanner(): LiveData<String> {
        launch {
            fetchBanner()
        }
        return bannerLiveData
    }

    private suspend fun fetchBanner() {
        val banner = withContext(Dispatchers.IO) {
            val photosString = PhotosUtils.photoJsonString()
            PhotosUtils.bannerFromJsonString(photosString ?: "")
        }

        if (banner != null) {
            bannerLiveData.value = banner
        }
    }

    private suspend fun fetchPhotos() {
        val photos = withContext(Dispatchers.IO) {
            val photosString = PhotosUtils.photoJsonString()
            PhotosUtils.photoUrlsFromJsonString(photosString ?: "")
        }

        if (photos != null) {
            photosLiveData.value = photos
        }
    }

    override fun registerLifecycle(lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun cancelJob() {
        Log.d(TAG, "cancelJob()")
        if (job.isActive) {
            Log.d(TAG, "Job active, canceling")
            job.cancel()
        }
    }
}