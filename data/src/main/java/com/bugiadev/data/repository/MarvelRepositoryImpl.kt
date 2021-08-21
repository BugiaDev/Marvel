package com.bugiadev.data.repository

import com.bugiadev.data.datasource.MarvelDataSource
import com.bugiadev.data.utils.mapNetworkErrors
import com.bugiadev.domain.models.CharacterModel
import com.bugiadev.domain.repository.MarvelRepository
import io.reactivex.Single
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val dataSource: MarvelDataSource
) : MarvelRepository {
    override fun getMarvelCharactersList(): Single<List<CharacterModel>> =
        dataSource.getCharacters().mapNetworkErrors().map { marvelEntity ->
            marvelEntity.data.characters?.map {
                it.toDomain()
            }
        }

    override fun getMarvelCharacterDetail(id: String): Single<CharacterModel> =
        dataSource.getCharacterDetail(id).mapNetworkErrors().map { marvelEntity ->
            marvelEntity.data.characters?.let {
                it[0].toDomain()
            }
        }
}