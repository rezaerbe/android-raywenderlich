package com.erbe.rwquotes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.erbe.rwquotes.utils.ioThread

const val DATABASE_VERSION_CODE = 3

@Database(entities = [Quote::class], version = DATABASE_VERSION_CODE, exportSchema = true)
abstract class RWQuotesDatabase : RoomDatabase() {

  abstract fun quotesDao(): QuotesDao

  companion object {
    private var INSTANCE: RWQuotesDatabase? = null

    fun getInstance(context: Context): RWQuotesDatabase? {
      if (INSTANCE == null) {
        synchronized(RWQuotesDatabase::class) {
          INSTANCE = Room.databaseBuilder(context,
              RWQuotesDatabase::class.java, "rwquotes_database.db")
              .addMigrations(MIGRATION_1_2)
              .addMigrations(MIGRATION_2_3)
              .allowMainThreadQueries()
              .addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                  super.onCreate(db)
                  ioThread {
                    for (q in PREPOPULATE_DATA) {
                      getInstance(context)!!.quotesDao().insertQuote(q)
                    }
                  }
                }
              })
              .build()
        }
      }
      return INSTANCE
    }

    val PREPOPULATE_DATA = listOf(Quote(1,
        "Any fool can write code that a computer can understand. Good programmers write code that humans can understand.",
        "Martin Fowler", "12/12/2020"),
        Quote(2, "First, solve the problem. Then, write the code.", "John Johnson", "12/12/2020"))
  }
}