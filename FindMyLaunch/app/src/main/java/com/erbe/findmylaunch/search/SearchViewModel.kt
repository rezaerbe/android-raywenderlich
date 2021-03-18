package com.erbe.findmylaunch.search

import android.text.Editable
import androidx.lifecycle.*
import com.erbe.findmylaunch.db.Launch
import com.erbe.findmylaunch.db.LaunchDao
import com.erbe.findmylaunch.db.calculateScore
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class SearchViewModel(
    private val launchDao: LaunchDao
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<Launch>>()
    val searchResults: LiveData<List<Launch>>
        get() = _searchResults

    init {
        fetchAllLaunches()
    }

    fun search(query: Editable?) {
        viewModelScope.launch {
            if (query.isNullOrBlank()) {
                launchDao.all().let {
                    _searchResults.postValue(it)
                }
            } else {
                val sanitizedQuery = sanitizeSearchQuery(query)
                launchDao.search(sanitizedQuery).let {
                    _searchResults.postValue(it)
                }
            }
        }
    }

    private fun sanitizeSearchQuery(query: Editable?): String {
        if (query == null) {
            return "";
        }
        val queryWithEscapedQuotes = query.replace(Regex.fromLiteral("\""), "\"\"")
        return "*\"$queryWithEscapedQuotes\"*"
    }

    private fun fetchAllLaunches() {
        viewModelScope.launch {
            launchDao.all().let { _searchResults.postValue(it) }
        }
    }

    fun searchWithScore(query: Editable?) {
        viewModelScope.launch {
            if (query.isNullOrBlank()) {
                launchDao.all().let { _searchResults.postValue(it) }
            } else {
                val sanitizedQuery = sanitizeSearchQuery(query)
                launchDao.searchWithMatchInfo(sanitizedQuery).let { results ->
                    results.sortedByDescending { result -> calculateScore(result.matchInfo) }
                        .map { result -> result.launch }
                        .let { _searchResults.postValue(it) }
                }
            }
        }
    }

    class Factory @Inject constructor(
        private val launchDao: Provider<LaunchDao>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(launchDao = launchDao.get()) as T
        }
    }
}