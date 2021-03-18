package com.erbe.listmaster.migrations

import androidx.annotation.VisibleForTesting
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@VisibleForTesting
class Migration1To2 : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS list_items" +
            "('item_description' TEXT NOT NULL, 'item_priority' INTEGER NOT NULL," +
            "'list_category_id' INTEGER NOT NULL, 'id' INTEGER NOT NULL, PRIMARY KEY(id)," +
            "FOREIGN KEY('list_category_id') REFERENCES list_categories('id') ON DELETE CASCADE)"
        )
    }
}