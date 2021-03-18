package com.erbe.rwnews.model

import com.erbe.common.mvp.Model
import com.erbe.rwnews.repository.entity.News


/**
 * The Model for the news
 */
data class NewsModel(val news: News) : Model