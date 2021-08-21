package com.bugiadev.marvel.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bugiadev.marvel.domain.repository.MarvelRepository
import com.bugiadev.marvel.ui.presentation.CharacterDisplay
import com.bugiadev.marvel.ui.presentation.toDisplay
import com.bugiadev.marvel.utils.prepareForUI
import com.bugiadev.marvel.utils.subscribe
import javax.inject.Inject

class MarvelDetailViewModel @Inject constructor(
    private val repository: MarvelRepository
) : BaseViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _marvelCharacterDetail = MutableLiveData<CharacterDisplay>()
    val marvelCharacterDetail: LiveData<CharacterDisplay> = _marvelCharacterDetail

    fun getCharacterDetail(id: String) {
        repository.getMarvelCharacterDetail(id = id)
            .doOnSubscribe { _loading.postValue(true) }
            .doFinally { _loading.postValue(false) }
            .prepareForUI()
            .subscribe(
                disposables = disposables,
                onSuccess = { characterDetail ->
                    _marvelCharacterDetail.postValue(characterDetail.toDisplay())
                },
                onError = ::handleError
            )
    }
}