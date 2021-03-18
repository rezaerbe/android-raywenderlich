package com.erbe.potterverse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PotterApi {

  @GET("api/characters")
  fun getCharacters(): Call<List<CharacterModel>>
//  fun getCharacters(@Query("key") key: String = API_KEY): Call<List<CharacterModel>>
}

// TODO : paste below your API key as a String value. You can get it from https://www.potterapi.com/
//  (do not forget to escape $ characters with a backslash if needed)
val API_KEY = ""
