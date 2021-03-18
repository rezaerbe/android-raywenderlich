package com.erbe.tennisplayer.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlayerDao {

    @Delete
    suspend fun deletePlayer(player: Player)

    @Update
    suspend fun updatePlayer(player: Player)

    @Query("SELECT * FROM players WHERE id = :id")
    fun getPlayer(id: Int): LiveData<Player>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllPlayers(players: List<Player>)

    @Query(value = "SELECT id, firstName, lastName, country, favorite, imageUrl FROM players")
    fun getAllPlayers(): LiveData<List<PlayerListItem>>
}