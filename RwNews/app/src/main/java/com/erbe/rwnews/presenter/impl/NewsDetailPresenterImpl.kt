package com.erbe.rwnews.presenter.impl

import android.util.Log
import com.erbe.common.mvp.impl.BasePresenter
import com.erbe.rwnews.conf.TAG
import com.erbe.rwnews.model.NewsModel
import com.erbe.rwnews.presenter.NewsDetailPresenter
import com.erbe.rwnews.repository.NewsRepository
import com.erbe.rwnews.stats.NewsStats
import com.erbe.rwnews.ui.detail.NewsDetailView
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


/**
 * Presenter for the display of the list
 */
@FragmentScoped
class NewsDetailPresenterImpl @Inject constructor(
    private val newsRepository: NewsRepository,
    private val newsStats: @JvmSuppressWildcards(true) Set<NewsStats>
) : BasePresenter<NewsModel, NewsDetailView>(),
    NewsDetailPresenter {

  override fun displayNewsDetail(newsId: Long) {
    Log.i(TAG, "In NewsDetailPresenterImpl using Repository $newsRepository")
    newsRepository.byId(newsId)?.let { news ->
      view?.displayNews(news)
      newsStats.forEach { stats ->
        stats.printStats(news)
      }
    }
  }
}