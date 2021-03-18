package com.erbe.bmicalc.log.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erbe.bmicalc.model.Person
import com.erbe.bmicalc.model.Repository

class LogViewModelFactory(
    private val repository: Repository,
    private val person: Person
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LogViewModel(repository, person) as T
    }
}