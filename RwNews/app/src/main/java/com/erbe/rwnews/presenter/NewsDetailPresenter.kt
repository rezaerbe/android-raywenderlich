package com.erbe.rwnews.presenter

import com.erbe.common.mvp.Presenter
import com.erbe.rwnews.model.NewsModel
import com.erbe.rwnews.ui.detail.NewsDetailView

/**
 * The Presenter for the News Detail
 */
interface NewsDetailPresenter : Presenter<NewsModel, NewsDetailView> {

  /**
   * Invoke in order to display the detail for the news
   */
  fun displayNewsDetail(newsId: Long)
}