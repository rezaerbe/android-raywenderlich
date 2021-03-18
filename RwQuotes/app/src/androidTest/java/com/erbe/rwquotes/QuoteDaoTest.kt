package com.erbe.rwquotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.erbe.rwquotes.data.Quote
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Testing the CRUD operations for DAO operations
 */
@RunWith(AndroidJUnit4::class)
open class QuoteDaoTest : DatabaseTest() {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @Test
  fun insertQuoteTest() {
    val quote = Quote(id = 1, text = "Hello World", author = "Ray Wenderlich", date = "27/12/1998")
    appDatabase.quotesDao().insertQuote(quote)
    val quotesSize = appDatabase.quotesDao().getQuotes().getValueBlocking()?.size
    assertEquals(quotesSize, 1)
  }

  @Test
  fun deleteQuoteTest() {
    val quote = Quote(id = 1, text = "Hello World", author = "Ray Wenderlich", date = "27/12/1998")
    appDatabase.quotesDao().insertQuote(quote)
    assertEquals(appDatabase.quotesDao().getQuotes().getValueBlocking()?.size, 1)
    appDatabase.quotesDao().deleteQuote(quote)
    assertEquals(appDatabase.quotesDao().getQuotes().getValueBlocking()?.size, 0)
  }

  @Test
  fun getQuoteAsLiveDataTest() {
    val quote = Quote(id = 1, text = "Hello World", author = "Ray Wenderlich", date = "27/12/1998")
    appDatabase.quotesDao().insertQuote(quote)
    val quoteLiveDataValue = appDatabase.quotesDao().getQuotes().getValueBlocking()
    assertEquals(quoteLiveDataValue?.size, 1)
  }

  @Test
  fun updateQuoteTest() {
    var quote = Quote(id = 1, text = "Hello World", author = "Ray Wenderlich", date = "27/12/1998")
    appDatabase.quotesDao().insertQuote(quote)
    quote.author = "Enzo Lizama"
    appDatabase.quotesDao().updateQuote(quote)
    assertEquals(appDatabase.quotesDao().getQuotes().getValueBlocking()?.get(0)?.author, "Enzo " +
        "Lizama")
  }
}