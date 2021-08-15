package com.bugiadev.marvel.data.datasource
import com.bugiadev.marvel.data.entities.MarvelEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelDataSource {
    companion object {
        const val CHARACTERS = "/v1/public/characters"
        const val CHARACTER = "/v1/public/characters/{characterId}"
    }

    @GET(CHARACTERS)
    fun getCharacters(
        @Query("limit") limit: Int? = 10,
        @Query("offset") offset: Int? = 0
    ): Single<MarvelEntity>

    /*
    TODO

        @GET("catalogo/209426-0-templos-catolicas.json")
    fun getCatholicChurches(): Single<CatholicChurchEntity>

        @GET(CHARACTERS)
    suspend fun getCharacters(
        @Query("limit") limit: Int? = 10,
        @Query("offset") offset: Int? = 0
    ): Response<BaseResponse<CharactersEntity>>

    @GET(CHARACTER)
    suspend fun getCharacter(@Path("characterId") id: String?): Response<BaseResponse<CharacterEntity>>
     */
}