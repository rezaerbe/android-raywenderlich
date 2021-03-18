package com.erbe.rwnews.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.erbe.rwnews.R
import com.erbe.rwnews.presenter.NewsDetailPresenter
import com.erbe.rwnews.repository.entity.News
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * The Fragment for the news detail
 */
@AndroidEntryPoint
class NewsDetailFragment : Fragment(), NewsDetailView {

  @Inject
  lateinit var newsDetailPresenter: NewsDetailPresenter

  private lateinit var newsTitleTextView: TextView
  private lateinit var newsBodyTextView: TextView

  companion object {

    const val NEWS_ID = "news_id"

    fun create(bundle: Bundle?): NewsDetailFragment = NewsDetailFragment().apply {
      arguments = bundle
    }
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.news_detail_layout, container, false)
    newsDetailPresenter.bind(this)
    newsTitleTextView = view.findViewById(R.id.news_detail_title)
    newsBodyTextView = view.findViewById(R.id.news_detail_body)
    return view
  }

  override fun onStart() {
    super.onStart()
    arguments?.getLong(NEWS_ID)?.let { newsId ->
      newsDetailPresenter.displayNewsDetail(newsId)
    }
  }

  override fun displayNews(news: News) {
    newsTitleTextView.text = news.title
    newsBodyTextView.text = news.body
  }

  override fun onDestroy() {
    super.onDestroy()
    newsDetailPresenter.unbind()
  }
}