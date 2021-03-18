package com.erbe.tourx.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.erbe.tourx.entities.Cost
import com.erbe.tourx.entities.Place

@Database(
    entities = [Place::class, Cost::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPlaceDao(): PlaceDao

    abstract fun getCostDao(): CostDao
}