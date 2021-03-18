package com.erbe.tennisplayer.details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.erbe.tennisplayer.database.Player
import com.erbe.tennisplayer.database.PlayerListItem
import com.erbe.tennisplayer.database.PlayerRepository
import com.erbe.tennisplayer.database.PlayersDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlayerRepository

    init {
        val playerDao = PlayersDatabase
            .getDatabase(application, viewModelScope, application.resources)
            .playerDao()
        repository = PlayerRepository(playerDao)
    }

    fun getPlayer(player: PlayerListItem): LiveData<Player> {
        return repository.getPlayer(player.id)
    }

    fun updatePlayer(player: Player) = viewModelScope.launch {
        repository.updatePlayer(player)
    }

    fun deletePlayer(player: Player) = viewModelScope.launch {
        repository.deletePlayer(player)
    }
}