package com.erbe.rwquotes.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erbe.rwquotes.data.QuotesRepository

class QuoteViewModelFactory(
    private val repository: QuotesRepository,
    private val application: Application
) : ViewModelProvider.Factory {
  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return QuotesViewModel(application, repository) as T
  }

}