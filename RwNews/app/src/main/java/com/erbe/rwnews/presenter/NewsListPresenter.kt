package com.erbe.rwnews.presenter

import com.erbe.common.mvp.Presenter
import com.erbe.rwnews.model.NewsListModel
import com.erbe.rwnews.ui.list.NewsListView


/**
 * The Presenter for the NewsList
 */
interface NewsListPresenter : Presenter<NewsListModel, NewsListView> {

  /**
   * Invoke in order to display the news
   */
  fun displayNewsList()
}