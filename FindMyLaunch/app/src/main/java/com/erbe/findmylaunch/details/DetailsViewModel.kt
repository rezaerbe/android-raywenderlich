package com.erbe.findmylaunch.details

import androidx.lifecycle.*
import com.erbe.findmylaunch.db.Launch
import com.erbe.findmylaunch.db.LaunchDao
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class DetailsViewModel(
    private val launchDao: LaunchDao
) : ViewModel() {

    private val _launch = MutableLiveData<Launch>()
    val launch: LiveData<Launch>
        get() = _launch

    fun fetchLaunchDetails(name: String) {
        viewModelScope.launch {
            launchDao.one(name).let {
                _launch.postValue(it)
            }
        }
    }

    class Factory @Inject constructor(
        private val launchDao: Provider<LaunchDao>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(launchDao.get()) as T
        }
    }
}