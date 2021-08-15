package com.bugiadev.marvel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bugiadev.marvel.domain.models.CharacterModel
import com.bugiadev.marvel.domain.repository.MarvelRepository
import com.bugiadev.marvel.utils.SingleLiveEvent
import com.bugiadev.marvel.utils.prepareForUI
import com.bugiadev.marvel.utils.subscribe
import javax.inject.Inject

class MarvelViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _marvelCharacters = MutableLiveData<List<CharacterModel>>()
    val marvelCharacters: LiveData<List<CharacterModel>> = _marvelCharacters

    private val _marvelCharacterDetail = MutableLiveData<CharacterModel>()
    val marvelCharacterDetail: LiveData<CharacterModel> = _marvelCharacterDetail

    private val _selectedCharacter = SingleLiveEvent<Int>()
    val selectedCharacter: LiveData<Int> = _selectedCharacter

    fun getCharactersList() {
        repository.getMarvelCharactersList()
            .doOnSubscribe { _loading.postValue(true) }
            .doFinally { _loading.postValue(false) }
            .prepareForUI()
            .subscribe(
                disposables = disposables,
                onSuccess = { characters ->
                    _marvelCharacters.postValue(characters)
                },
                onError = ::handleError
            )
    }

    fun getCharacterDetail(id: String) {
        repository.getMarvelCharacterDetail(id = id)
            .doOnSubscribe { _loading.postValue(true) }
            .doFinally { _loading.postValue(false) }
            .prepareForUI()
            .subscribe(
                disposables = disposables,
                onSuccess = { characterDetail ->
                    _marvelCharacterDetail.postValue(characterDetail)
                },
                onError = ::handleError
            )
    }

    fun onCharacterSelected(id: Int) {
        _selectedCharacter.postValue(id)
    }
}