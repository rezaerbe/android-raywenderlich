package com.erbe.ingredisearch.data

interface RecipeRepository {
  fun addFavorite(item: Recipe)
  fun removeFavorite(item: Recipe)
  fun getFavoriteRecipes(): List<Recipe>
  fun getRecipes(query: String, callback: RepositoryCallback<List<Recipe>>)
}

interface RepositoryCallback<in T> {
  fun onSuccess(t: T?)
  fun onError()
}