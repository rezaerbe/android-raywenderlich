package com.erbe.tennisplayer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.erbe.tennisplayer.database.PlayerListItem
import com.erbe.tennisplayer.database.PlayerRepository
import com.erbe.tennisplayer.database.PlayersDatabase

open class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlayerRepository

    init {
        val playerDao = PlayersDatabase
            .getDatabase(application, viewModelScope, application.resources)
            .playerDao()
        repository = PlayerRepository(playerDao)
    }

    fun getAllPlayers(): LiveData<List<PlayerListItem>> {
        return repository.getAllPlayers()
    }
}