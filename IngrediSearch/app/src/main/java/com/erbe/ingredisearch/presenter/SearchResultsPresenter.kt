package com.erbe.ingredisearch.presenter

import com.erbe.ingredisearch.data.Recipe
import com.erbe.ingredisearch.data.RecipeRepository
import com.erbe.ingredisearch.data.RepositoryCallback


class SearchResultsPresenter(private val repository: RecipeRepository) :
    BasePresenter<SearchResultsPresenter.View>() {
  private var recipes: List<Recipe>? = null

  fun search(query: String) {
    view?.showLoading()

    repository.getRecipes(query, object : RepositoryCallback<List<Recipe>> {
      override fun onSuccess(recipes: List<Recipe>?) {
        this@SearchResultsPresenter.recipes = recipes
        if (recipes != null && recipes.isNotEmpty()) {
          view?.showRecipes(recipes)
        } else {
          view?.showEmptyRecipes()
        }
      }

      override fun onError() {
        view?.showError()
      }
    })
  }

  fun addFavorite(recipe: Recipe) {
    recipe.isFavorited = true

    repository.addFavorite(recipe)

    val recipeIndex = recipes?.indexOf(recipe)
    if (recipeIndex != null) {
      view?.refreshFavoriteStatus(recipeIndex)
    }
  }


  fun removeFavorite(recipe: Recipe) {
    repository.removeFavorite(recipe)
    recipe.isFavorited = false
    val recipeIndex = recipes?.indexOf(recipe)
    if (recipeIndex != null) {
      view?.refreshFavoriteStatus(recipeIndex)
    }
  }

  interface View {
    fun showLoading()
    fun showRecipes(recipes: List<Recipe>)
    fun showEmptyRecipes()
    fun showError()
    fun refreshFavoriteStatus(recipeIndex: Int)
  }
}