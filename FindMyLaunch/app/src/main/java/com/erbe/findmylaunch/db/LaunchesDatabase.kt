package com.erbe.findmylaunch.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Launch::class, LaunchFTS::class],
    exportSchema = false,
    version = 2
)
abstract class LaunchesDatabase : RoomDatabase() {
    abstract fun launchDao(): LaunchDao
}