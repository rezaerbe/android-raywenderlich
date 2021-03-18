package com.erbe.findmylaunch.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.erbe.findmylaunch.db.LaunchDao
import com.erbe.findmylaunch.db.LaunchesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun launchesDB(context: Context): LaunchesDatabase {
        return Room.databaseBuilder(context, LaunchesDatabase::class.java, "findmylaunch.db")
            .createFromAsset("launches.db")
            .addCallback(object : RoomDatabase.Callback() { // Add this callback
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL("INSERT INTO launches_fts(launches_fts) VALUES ('rebuild')")
                }
            })
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun launchesDAO(db: LaunchesDatabase): LaunchDao {
        return db.launchDao()
    }
}