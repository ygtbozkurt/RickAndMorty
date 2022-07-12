package com.example.rickandmorty.data.remote

import com.example.rickandmorty.data.model.RickAndMortyCharacter
import com.example.rickandmorty.data.model.RickAndMortyCharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {
    @GET("character")
    suspend fun getAllCharacters(): Response<RickAndMortyCharacterList>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<RickAndMortyCharacter>
}