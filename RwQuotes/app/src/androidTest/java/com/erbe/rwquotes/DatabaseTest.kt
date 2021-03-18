package com.erbe.rwquotes

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.erbe.rwquotes.data.RWQuotesDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
abstract class DatabaseTest {

  protected lateinit var appDatabase: RWQuotesDatabase

  @Before
  fun initDb() {
    appDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        RWQuotesDatabase::class.java)
        .allowMainThreadQueries()
        .build()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    appDatabase.close()
  }
}