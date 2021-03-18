package com.erbe.listmaster.migrations

import androidx.annotation.VisibleForTesting
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@VisibleForTesting
class Migration2To3 : Migration(2, 3) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE INDEX 'index_list_category_id' ON list_items('list_category_id')"
        )
    }
}