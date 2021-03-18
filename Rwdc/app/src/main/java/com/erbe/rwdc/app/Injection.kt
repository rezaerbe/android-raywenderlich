package com.erbe.rwdc.app

import androidx.lifecycle.Lifecycle
import com.erbe.rwdc.repository.PhotosRepository
import com.erbe.rwdc.repository.Repository
import com.erbe.rwdc.ui.photos.PhotosViewModelFactory

object Injection {

    private fun provideRepository(): Repository {
        return PhotosRepository()
    }

    fun provideViewModelFactory(lifecycle: Lifecycle): PhotosViewModelFactory {
        val repository = provideRepository()
        repository.registerLifecycle(lifecycle)
        return PhotosViewModelFactory(repository)
    }
}