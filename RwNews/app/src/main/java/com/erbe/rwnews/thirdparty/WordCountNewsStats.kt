package com.erbe.rwnews.thirdparty

import android.util.Log
import com.erbe.rwnews.repository.entity.News
import com.erbe.rwnews.stats.NewsStats
import com.erbe.rwnews.stats.STATS_LOG

class WordCountNewsStats : NewsStats {
  override fun printStats(news: News) {
    val wordsCount = news.body.splitToSequence(" ").count()
    Log.i(STATS_LOG, "News Word count: $wordsCount")
  }
}