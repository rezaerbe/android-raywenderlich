package com.erbe.smallvictories

interface VictoryRepository {
  fun getVictoryTitleAndCount(): Pair<String, Int>
  fun setVictoryTitle(title: String)
  fun getVictoryTitle(): String
  fun setVictoryCount(count: Int)
  fun getVictoryCount(): Int
  fun clear()
}