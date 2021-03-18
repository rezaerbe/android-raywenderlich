package com.erbe.ingredisearch

import android.content.SharedPreferences
import com.erbe.ingredisearch.data.Recipe
import com.erbe.ingredisearch.data.RecipeRepository
import com.erbe.ingredisearch.data.RecipeRepositoryImpl
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test

class RepositoryTests {
  private lateinit var spyRepository: RecipeRepository
  private lateinit var sharedPreferences: SharedPreferences
  private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

  @Before
  fun setup() {
    sharedPreferences = mock()
    sharedPreferencesEditor = mock()
    whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)

    spyRepository = spy(RecipeRepositoryImpl(sharedPreferences))
  }

  @Test
  fun addFavorite_withEmptyRecipes_savesJsonRecipe() {
    doReturn(emptyList<Recipe>()).whenever(spyRepository).getFavoriteRecipes()

    val recipe = Recipe("id", "title", "imageUrl", "sourceUrl", false)
    spyRepository.addFavorite(recipe)

    inOrder(sharedPreferencesEditor) {
      val jsonString = Gson().toJson(listOf(recipe))
      verify(sharedPreferencesEditor).putString(any(), eq(jsonString))
      verify(sharedPreferencesEditor).apply()
    }
  }
}