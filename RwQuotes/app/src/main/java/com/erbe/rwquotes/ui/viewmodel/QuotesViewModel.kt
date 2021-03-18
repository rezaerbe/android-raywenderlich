package com.erbe.rwquotes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.erbe.rwquotes.data.Quote
import com.erbe.rwquotes.data.QuotesRepository
import kotlinx.coroutines.launch

class QuotesViewModel(application: Application, private val repository: QuotesRepository) :
    AndroidViewModel(application) {

  private val _dataLoading = MutableLiveData<Boolean>()
  val dataLoading: LiveData<Boolean> = _dataLoading

  init {
    _dataLoading.value = true
  }


  fun insertQuote(quote: Quote) {
    _dataLoading.postValue(true)
    viewModelScope.launch {
      repository.insert(quote)
    }
    _dataLoading.postValue(false)
  }

  fun updateQuote(quote: Quote) {
    _dataLoading.postValue(true)
    viewModelScope.launch {
      repository.update(quote)
    }
    _dataLoading.postValue(false)
  }

  fun delete(quote: Quote) {
    _dataLoading.postValue(true)
    viewModelScope.launch {
      repository.delete(quote)
    }
    _dataLoading.postValue(false)
  }

  fun getAllQuotes(): LiveData<List<Quote>> {
    var quotes: LiveData<List<Quote>>? = null
    _dataLoading.postValue(true)
    viewModelScope.launch {
      quotes = repository.getQuotes()
    }
    _dataLoading.postValue(false)
    return quotes!!
  }
}