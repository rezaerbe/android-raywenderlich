package com.erbe.smallvictories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VictoryViewModel : ViewModel() {

  val viewState: MutableLiveData<VictoryUiModel> = MutableLiveData()
  lateinit var repository: VictoryRepository

  fun initialize() {
    val (title, count) = repository.getVictoryTitleAndCount()
    viewState.value = VictoryUiModel.TitleUpdated(title)
    viewState.value = VictoryUiModel.CountUpdated(count)
  }

  fun setVictoryTitle(title: String) {
    repository.setVictoryTitle(title)
    viewState.value = VictoryUiModel.TitleUpdated(title)
  }

  fun incrementVictoryCount() {
    val newCount = repository.getVictoryCount() + 1
    repository.setVictoryCount(newCount)
    viewState.value = VictoryUiModel.CountUpdated(newCount)
  }

  fun reset() {
    repository.clear()

    val (title, count) = repository.getVictoryTitleAndCount()
    viewState.value = VictoryUiModel.CountUpdated(count)
    viewState.value = VictoryUiModel.TitleUpdated(title)
  }
}

