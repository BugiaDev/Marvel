package com.bugiadev.marvel.data.repository

import com.bugiadev.marvel.data.datasource.MarvelDataSource
import com.bugiadev.marvel.domain.models.CharacterModel
import com.bugiadev.marvel.domain.repository.MarvelRepository
import com.bugiadev.marvel.utils.mapNetworkErrors
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