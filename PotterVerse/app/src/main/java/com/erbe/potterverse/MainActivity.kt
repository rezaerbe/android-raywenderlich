/*
 * Copyright (c) 2020 Razeware LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 * 
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.erbe.potterverse

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

  private lateinit var characterAdapter: CharacterAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.Theme_PotterVerse)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Your code
    characterAdapter = CharacterAdapter()
    character_recyclerview.adapter = characterAdapter

    val potterApi = Retrofit.Builder()
        .baseUrl((application as PotterApp).getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(OkHttpProvider.getOkHttpClient())
        .build()
        .create(PotterApi::class.java)

    potterApi.getCharacters().enqueue(object : Callback<List<CharacterModel>> {
      override fun onFailure(call: Call<List<CharacterModel>>, t: Throwable) {
        showErrorState()
      }

      override fun onResponse(call: Call<List<CharacterModel>>,
                              response: Response<List<CharacterModel>>) {
        if (response.isSuccessful && response.body() != null) {
          val characterList = response.body()!!
          if (characterList.isEmpty()) {
            showEmptyDataState()
          } else {
            showCharacterList(characterList)
          }
        } else {
          showErrorState()
        }
      }
    })
  }

  private fun showEmptyDataState() {
    character_recyclerview.visibility = View.GONE
    progress_bar.visibility = View.GONE
    textview.visibility = View.VISIBLE
    textview.text = getString(R.string.there_seems_to_be_no_data)
  }

  private fun showCharacterList(characterList: List<CharacterModel>) {
    character_recyclerview.visibility = View.VISIBLE
    progress_bar.visibility = View.GONE
    textview.visibility = View.GONE
    characterAdapter.setCharacterList(characterList)
  }

  private fun showErrorState() {
    character_recyclerview.visibility = View.GONE
    progress_bar.visibility = View.GONE
    textview.visibility = View.VISIBLE
    textview.text = getString(R.string.something_went_wrong)
  }
}
