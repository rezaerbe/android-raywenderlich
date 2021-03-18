package com.erbe.rwnews.stats

import android.util.Log
import com.erbe.rwnews.repository.entity.News

class LengthNewsStats : NewsStats {
  override fun printStats(news: News) {
    Log.i(STATS_LOG, "News Length: ${news.body.length}")
  }
}