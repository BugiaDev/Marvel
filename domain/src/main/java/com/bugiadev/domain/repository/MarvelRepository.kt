package com.bugiadev.domain.repository

import com.bugiadev.domain.models.CharacterModel
import io.reactivex.Single

interface MarvelRepository {
    fun getMarvelCharactersList(): Single<List<CharacterModel>>
    fun getMarvelCharacterDetail(id: String): Single<CharacterModel>
}