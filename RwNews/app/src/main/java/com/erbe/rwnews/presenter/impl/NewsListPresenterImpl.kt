package com.erbe.rwnews.presenter.impl

import android.util.Log
import com.erbe.common.mvp.impl.BasePresenter
import com.erbe.rwnews.conf.TAG
import com.erbe.rwnews.model.NewsListModel
import com.erbe.rwnews.presenter.NewsListPresenter
import com.erbe.rwnews.repository.NewsRepository
import com.erbe.rwnews.ui.list.NewsListView
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


/**
 * Presenter for the display of the list
 */
@FragmentScoped
class NewsListPresenterImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : BasePresenter<NewsListModel, NewsListView>(),
    NewsListPresenter {

  override fun displayNewsList() {
    Log.i(TAG, "In NewsListPresenterImpl using Repository $newsRepository")
    val newsList = newsRepository.list()
    view?.displayNews(NewsListModel(newsList))
  }
}