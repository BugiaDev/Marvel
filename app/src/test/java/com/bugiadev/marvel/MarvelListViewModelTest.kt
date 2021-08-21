package com.bugiadev.marvel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bugiadev.marvel.domain.models.CharacterModel
import com.bugiadev.marvel.domain.models.ThumbnailModel
import com.bugiadev.marvel.domain.repository.MarvelRepository
import com.bugiadev.marvel.lifecycle.observeOnce
import com.bugiadev.marvel.rules.RxSchedulerRule
import com.bugiadev.presentation.viewmodel.MarvelListViewModel
import com.bugiadev.presentation.viewmodel.UnexpectedError
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.subjects.SingleSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MarvelListViewModelTest {
    @get:Rule
    val schedulerRule = RxSchedulerRule()

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    private val repository: MarvelRepository = mockk()
    lateinit var viewModel: MarvelListViewModel

    @Before
    fun setUp() {
        viewModel = MarvelListViewModel(repository)
    }

    @Test
    fun `character list are retrieved successfully`() {
        // Given
        val subject = SingleSubject.create<List<CharacterModel>>()
        val model = mockCharacter()
        every { repository.getMarvelCharactersList() } returns subject

        // When
        viewModel.getCharactersList()

        // Then
        verify(exactly = 1) { repository.getMarvelCharactersList() }
        viewModel.loading.observeOnce { assert(it) }
        subject.onSuccess(listOf(model))
        viewModel.loading.observeOnce { assert(!it) }
        viewModel.marvelCharacters.observeOnce {
            it.size == 1 && it[0].id == model.id
        }
    }

    @Test
    fun `character list are not retrieved due to an error`() {
        // Given
        val subject = SingleSubject.create<List<CharacterModel>>()
        every { repository.getMarvelCharactersList() } returns subject

        // When
        viewModel.getCharactersList()

        // Then
        verify(exactly = 1) { repository.getMarvelCharactersList() }
        viewModel.loading.observeOnce { assert(it) }
        subject.onError(Throwable())
        viewModel.loading.observeOnce { assert(!it) }
        viewModel.error.observeOnce { assert(it is UnexpectedError) }
    }

    @Test
    fun `on character selected by user then value is set correctly`() {
        // Given
        val id = 1234

        // When
        viewModel.onCharacterSelected(id)

        // Then
        viewModel.selectedCharacter.observeOnce {
            assert(it == id)
        }
    }

    // region MOCKS
    private fun mockCharacter() = CharacterModel(
        id = 1234,
        name = "super name",
        description = "super description",
        modified = "21/07/2021 14:55",
        resourceURI = "http://marvel.hero.com/1234",
        characterImage = ThumbnailModel(
            path = "http://marvel.hero-image.com/1234",
            extension = "jpg"
        )
    )
    // endregion
}