package com.erbe.rwnews.ui.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.erbe.common.Binder
import com.erbe.common.events.OnViewHolderItemSelected
import com.erbe.rwnews.R
import com.erbe.rwnews.repository.entity.News

/**
 * The ViewHolder for the NewsList RecyclerView Adapter
 */
class NewsListItemViewHolder(
    private val view: View,
    listener: OnViewHolderItemSelected<News?>? = null
) : RecyclerView.ViewHolder(view), Binder<News> {

  private val mTitleTextView: TextView = view.findViewById(R.id.news_title)
  private val mBodyTextView: TextView = view.findViewById(R.id.news_body)

  private var currentNews: News? = null

  init {
    listener?.let { l ->
      view.setOnClickListener { l(currentNews) }
    }
  }

  override fun bind(model: News) {
    currentNews = model
    model.run {
      mTitleTextView.text = title
      mBodyTextView.text = body
    }
  }
}