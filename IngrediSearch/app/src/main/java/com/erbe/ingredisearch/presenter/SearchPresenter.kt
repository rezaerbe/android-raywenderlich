package com.erbe.ingredisearch.presenter

class SearchPresenter: BasePresenter<SearchPresenter.View>(){

  fun search(query: String) {
    if (query.trim().isBlank()) {
      view?.showQueryRequiredMessage()
    } else {
      view?.showSearchResults(query)
    }
  }

  interface View {
    fun showQueryRequiredMessage()
    fun showSearchResults(query: String)
  }
}