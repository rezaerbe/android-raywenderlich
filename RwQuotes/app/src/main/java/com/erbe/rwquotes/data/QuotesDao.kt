package com.erbe.rwquotes.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuotesDao {

  @Query("SELECT * FROM rwquotes ORDER BY id DESC")
  fun getQuotes(): LiveData<List<Quote>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertQuote(quote: Quote) : Long

  @Update
  fun updateQuote(quote: Quote): Int

  @Delete
  fun deleteQuote(quote: Quote): Int
}