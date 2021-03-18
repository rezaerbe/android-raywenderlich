
package com.erbe.ingredisearch.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.erbe.ingredisearch.R
import com.erbe.ingredisearch.presenter.SearchPresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : ChildActivity(), SearchPresenter.View {

  private val presenter: SearchPresenter = SearchPresenter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)

    presenter.attachView(this)

    searchButton.setOnClickListener {
      val query = ingredients.text.toString()
      presenter.search(query)
    }
  }

  override fun onDestroy() {
    presenter.detachView()
    super.onDestroy()
  }

  override fun showQueryRequiredMessage() {
    // Hide keyboard
    val view = this.currentFocus
    if (view != null) {
      val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    Snackbar.make(searchButton, getString(R.string.search_query_required), Snackbar
        .LENGTH_LONG).show()
  }

  override fun showSearchResults(query: String) {
    startActivity(searchResultsIntent(query))
  }
}
