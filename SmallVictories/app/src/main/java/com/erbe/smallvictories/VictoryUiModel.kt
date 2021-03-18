package com.erbe.smallvictories

sealed class VictoryUiModel {

  data class TitleUpdated(val title: String) : VictoryUiModel()

  data class CountUpdated(val count: Int) : VictoryUiModel()
}
