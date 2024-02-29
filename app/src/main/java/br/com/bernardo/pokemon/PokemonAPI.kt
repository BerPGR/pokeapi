package br.com.bernardo.pokemon

import br.com.bernardo.pokemon.model.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonAPI {

    @GET("{name}")
    suspend fun getPokemonByName(@Path("name") name: String): Response<Pokemon>
}