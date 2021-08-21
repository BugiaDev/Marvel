package com.bugiadev.data.datasource
import com.bugiadev.data.entities.MarvelEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelDataSource {
    companion object {
        const val CHARACTERS = "/v1/public/characters"
        const val CHARACTER = "/v1/public/characters/{characterId}"
    }

    @GET(CHARACTERS)
    fun getCharacters(
        @Query("limit") limit: Int? = 10,
        @Query("offset") offset: Int? = 1
    ): Single<MarvelEntity>

    @GET(CHARACTER)
    fun getCharacterDetail(
        @Path("characterId") characterId: String
    ): Single<MarvelEntity>
}