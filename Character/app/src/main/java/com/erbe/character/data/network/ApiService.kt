package com.erbe.character.data.network

import com.erbe.character.data.models.CharactersResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/api/character/")
    suspend fun getCharacters(): Response<CharactersResponseModel>
}