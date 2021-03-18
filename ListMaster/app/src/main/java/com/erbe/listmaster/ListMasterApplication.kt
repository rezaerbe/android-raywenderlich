package com.erbe.listmaster

import android.app.Application
import androidx.room.Room
import com.erbe.listmaster.database.AppDatabase

class ListMasterApplication : Application() {

    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this, AppDatabase::class.java, "list-master-db")
            .addMigrations(AppDatabase.MIGRATION_1_TO_2)
            .addMigrations(AppDatabase.MIGRATION_2_TO_3)
            .build()
    }
}