package com.erbe.cheesefinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Cheese::class)], version = 1)
abstract class CheeseDatabase : RoomDatabase() {

    abstract fun cheeseDao(): CheeseDao

    companion object {

        private var INSTANCE: CheeseDatabase? = null

        fun getInstance(context: Context): CheeseDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context,
                        CheeseDatabase::class.java, "cheese.db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as CheeseDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}