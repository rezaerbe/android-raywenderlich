package com.erbe.rwquotes.data

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class QuotesRepositoryImpl(application: Application) : QuotesRepository, CoroutineScope {

  private val quotesDao: QuotesDao
  private val job: Job

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

  init {
    val database = RWQuotesDatabase.getInstance(application.applicationContext)
    quotesDao = database!!.quotesDao()
    job = Job()
  }

  override fun insert(quote: Quote) {
    launch(Dispatchers.IO) {
      quotesDao.insertQuote(quote)
    }
  }

  override fun update(quote: Quote) {
    launch(Dispatchers.IO) { quotesDao.updateQuote(quote) }
  }

  override fun delete(quote: Quote) {
    launch(Dispatchers.IO) { quotesDao.deleteQuote(quote) }
  }

  override fun getQuotes(): LiveData<List<Quote>> {
    return quotesDao.getQuotes()
  }
}