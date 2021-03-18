package com.erbe.tennisplayer.database

import androidx.lifecycle.LiveData

class PlayerRepository(private val playerDao: PlayerDao) {

    fun getAllPlayers(): LiveData<List<PlayerListItem>> {
        return playerDao.getAllPlayers()
    }

    fun getPlayer(id: Int): LiveData<Player> {
        return playerDao.getPlayer(id)
    }

    suspend fun updatePlayer(player: Player) {
        playerDao.updatePlayer(player)
    }

    suspend fun deletePlayer(player: Player) {
        playerDao.deletePlayer(player)
    }
}