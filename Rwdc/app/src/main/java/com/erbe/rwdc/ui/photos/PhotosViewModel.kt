package com.erbe.rwdc.ui.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.erbe.rwdc.repository.Repository

class PhotosViewModel(private val repository: Repository) : ViewModel() {

    fun getPhotos(): LiveData<List<String>> {
        return repository.getPhotos()
    }

    fun getBanner(): LiveData<String> {
        return repository.getBanner()
    }
}