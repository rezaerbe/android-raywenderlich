package com.erbe.tennisplayer.database

import android.content.Context
import android.content.res.Resources
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.erbe.tennisplayer.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(version = 1, entities = [Player::class], exportSchema = false)
abstract class PlayersDatabase : RoomDatabase() {

    abstract fun playerDao(): PlayerDao

    private class PlayerDatabaseCallback(
        private val scope: CoroutineScope,
        private val resources: Resources
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val playerDao = database.playerDao()
                    prePopulateDatabase(playerDao)
                }
            }
        }

        private suspend fun prePopulateDatabase(playerDao: PlayerDao) {
            val jsonString = resources.openRawResource(R.raw.players).bufferedReader().use {
                it.readText()
            }
            val typeToken = object : TypeToken<List<Player>>() {}.type
            val tennisPlayers = Gson().fromJson<List<Player>>(jsonString, typeToken)
            playerDao.insertAllPlayers(tennisPlayers)
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: PlayersDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope, resources: Resources): PlayersDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    PlayersDatabase::class.java,
                    "players_database")
                    .addCallback(PlayerDatabaseCallback(coroutineScope, resources))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}