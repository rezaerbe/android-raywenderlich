package com.erbe.rwquotes.data

import androidx.annotation.VisibleForTesting
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@VisibleForTesting
val MIGRATION_1_2 = object : Migration(1, 2) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE rwquotes ADD COLUMN 'stars' INTEGER NOT NULL DEFAULT 0")
  }
}

@VisibleForTesting
val MIGRATION_2_3 = object : Migration(2, 3) {
  override fun migrate(database: SupportSQLiteDatabase) {

  }
}