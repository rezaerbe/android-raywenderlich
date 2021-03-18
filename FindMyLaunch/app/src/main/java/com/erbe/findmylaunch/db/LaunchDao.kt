package com.erbe.findmylaunch.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(launch: Launch)

    @Query("SELECT * FROM launches")
    suspend fun all(): List<Launch>

    @Query("SELECT * FROM launches WHERE launches.name = :name")
    suspend fun one(name: String): Launch

    @Query("""
        SELECT *
        FROM launches
        JOIN launches_fts ON launches.name = launches_fts.name
        WHERE launches_fts MATCH :query
        """)
    suspend fun search(query: String): List<Launch>

    @Query("""
        SELECT *, matchinfo(launches_fts) as matchInfo
        FROM launches
        JOIN launches_fts ON launches.name = launches_fts.name
        WHERE launches_fts MATCH :query
        """)
    suspend fun searchWithMatchInfo(query: String): List<LaunchWithMatchInfo>
}