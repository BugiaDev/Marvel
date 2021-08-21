package com.bugiadev.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bugiadev.domain.repository.MarvelRepository
import com.bugiadev.presentation.display.CharacterDisplay
import com.bugiadev.presentation.display.toDisplay
import com.bugiadev.presentation.utils.prepareForUI
import com.bugiadev.presentation.utils.subscribe
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