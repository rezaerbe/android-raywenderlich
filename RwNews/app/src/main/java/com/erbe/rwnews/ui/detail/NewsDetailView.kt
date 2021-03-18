package com.erbe.rwnews.ui.detail

import com.erbe.common.mvp.View
import com.erbe.rwnews.model.NewsModel
import com.erbe.rwnews.repository.entity.News


/**
 * The View for the Detail
 */
interface NewsDetailView : View<NewsModel> {

  /**
   * Displays the given News
   */
  fun displayNews(news: News)
}