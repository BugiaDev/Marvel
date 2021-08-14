package com.bugiadev.marvel.data.repository

import com.bugiadev.marvel.data.datasource.MarvelDataSource
import com.bugiadev.marvel.domain.models.CharacterModel
import com.bugiadev.marvel.domain.repository.MarvelRepository
import io.reactivex.Single
import javax.inject.Inject

class MarvelRepositoryImpl @Inject constructor(
    private val dataSource: MarvelDataSource
) : MarvelRepository {
    override fun getMarvelCharactersList(): Single<List<CharacterModel>> {
        TODO("Not yet implemented")
    }

    override fun getMarvelCharacterDetail(): Single<CharacterModel> {
        TODO("Not yet implemented")
    }
}