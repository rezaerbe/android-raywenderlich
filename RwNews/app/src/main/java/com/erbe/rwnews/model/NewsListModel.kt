package com.erbe.rwnews.model

import com.erbe.common.mvp.Model
import com.erbe.rwnews.repository.entity.News

/**
 * Model for the NewsList
 */
data class NewsListModel(var newsList: List<News>) : Model