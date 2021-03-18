package com.erbe.rwnews.stats

import com.erbe.rwnews.repository.entity.News

const val STATS_LOG = "NEWS_STATS"

interface NewsStats {

  fun printStats(news: News)
}