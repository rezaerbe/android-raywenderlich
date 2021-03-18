package com.erbe.listmaster.database

import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.erbe.listmaster.migrations.Migration1To2
import com.erbe.listmaster.migrations.Migration2To3
import com.erbe.listmaster.model.ListCategory
import com.erbe.listmaster.model.ListItem

@Database(entities = [ListCategory::class, ListItem::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun listCategoryDao(): ListCategoryDao

    abstract fun listItemDao(): ListItemDao

    companion object {

        @VisibleForTesting
        val MIGRATION_1_TO_2 = Migration1To2()

        @VisibleForTesting
        val MIGRATION_2_TO_3 = Migration2To3()
    }
}