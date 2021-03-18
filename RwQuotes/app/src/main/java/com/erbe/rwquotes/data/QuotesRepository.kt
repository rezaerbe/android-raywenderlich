package com.erbe.rwquotes.data

import androidx.lifecycle.LiveData

interface QuotesRepository {

  fun insert(quote: Quote)

  fun update(quote: Quote)

  fun delete(quote: Quote)

  fun getQuotes(): LiveData<List<Quote>>
}