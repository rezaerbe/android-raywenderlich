package com.erbe.rwquotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.erbe.rwquotes.data.MIGRATION_1_2
import com.erbe.rwquotes.data.MIGRATION_2_3

import com.erbe.rwquotes.data.RWQuotesDatabase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RWQuoteMigrationTest {

  private lateinit var database: SupportSQLiteDatabase

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  companion object {
    private const val TEST_DB = "migration-test"
  }

  @get:Rule
  val migrationTestHelper = MigrationTestHelper(
      InstrumentationRegistry.getInstrumentation(),
      RWQuotesDatabase::class.java.canonicalName,
      FrameworkSQLiteOpenHelperFactory()
  )


  @Test
  fun migrate1to2() {
    database = migrationTestHelper.createDatabase(TEST_DB, 1).apply {

      execSQL(
          """
                INSERT INTO rwquotes VALUES (10, 'Hello', 'Shakespeare', '12/12/21')
                """.trimIndent()
      )
      close()
    }

    database = migrationTestHelper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)

    val resultCursor = database.query("SELECT * FROM rwquotes")

    // Let's make sure we can find the  age column, and that it's equal to our default.
    // We can also validate the name is the one we inserted.
    assertTrue(resultCursor.moveToFirst())

    val authorColumnIndex = resultCursor.getColumnIndex("author")
    val textColumnIndex = resultCursor.getColumnIndex("text")

    val authorFromDatabase = resultCursor.getString(authorColumnIndex)
    val textFromDatabase = resultCursor.getString(textColumnIndex)

    assertEquals("Shakespeare", authorFromDatabase)
    assertEquals("Hello", textFromDatabase)
  }

  private val ALL_MIGRATIONS = arrayOf(MIGRATION_1_2, MIGRATION_2_3)

  /**
   * Testing all migrations
   */
  @Test
  @Throws(IOException::class)
  fun migrateAll() {
    // Create earliest version of the database.
    migrationTestHelper.createDatabase(TEST_DB, 2).apply {
      close()
    }

    // Open latest version of the database. Room will validate the schema
    // once all migrations execute.
    Room.databaseBuilder(
        InstrumentationRegistry.getInstrumentation().targetContext,
        RWQuotesDatabase::class.java,
        TEST_DB
    ).addMigrations(*ALL_MIGRATIONS).build().apply {
      openHelper.writableDatabase
      close()
    }
  }
}