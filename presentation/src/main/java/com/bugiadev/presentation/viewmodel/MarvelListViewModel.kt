package com.bugiadev.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bugiadev.domain.repository.MarvelRepository
import com.bugiadev.presentation.display.CharacterDisplay
import com.bugiadev.presentation.display.toDisplay
import com.bugiadev.presentation.utils.SingleLiveEvent
import com.bugiadev.presentation.utils.prepareForUI
import com.bugiadev.presentation.utils.subscribe
import javax.inject.Inject

class MarvelListViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _marvelCharacters = MutableLiveData<List<CharacterDisplay>>()
    val marvelCharacters: LiveData<List<CharacterDisplay>> = _marvelCharacters

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
                    _marvelCharacters.postValue(characters.map { it.toDisplay() })
                },
                onError = ::handleError
            )
    }

    fun onCharacterSelected(id: Int) {
        _selectedCharacter.postValue(id)
    }
}