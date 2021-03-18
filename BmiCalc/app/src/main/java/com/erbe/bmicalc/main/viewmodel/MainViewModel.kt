package com.erbe.bmicalc.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erbe.bmicalc.main.model.MainUIModel
import com.erbe.bmicalc.model.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {

    val _logs = MutableLiveData<MainUIModel>()
    val logs: LiveData<MainUIModel> = _logs

    fun loadLogs() {
        val person = repository.getPerson()
        person?.let {
            _logs.value = MainUIModel.ExistingProfile(it)
        } ?: run {
            _logs.value = MainUIModel.MissingProfile
        }
    }
}