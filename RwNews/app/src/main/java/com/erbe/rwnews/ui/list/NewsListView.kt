package com.erbe.rwnews.ui.list

import com.erbe.common.mvp.View
import com.erbe.rwnews.model.NewsListModel


/**
 * This is a View in a MVP architecture for News list
 */
interface NewsListView : View<NewsListModel> {

  /**
   * Displays the list of news in the View
   */
  fun displayNews(newsList: NewsListModel)
}