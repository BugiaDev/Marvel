package com.bugiadev.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bugiadev.domain.models.CharacterModel
import com.bugiadev.domain.models.ThumbnailModel
import com.bugiadev.domain.repository.MarvelRepository
import com.bugiadev.presentation.lifecycle.observeOnce
import com.bugiadev.presentation.rules.RxSchedulerRule
import com.bugiadev.presentation.viewmodel.MarvelDetailViewModel
import com.bugiadev.presentation.viewmodel.UnexpectedError
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.subjects.SingleSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MarvelDetailViewModelTest {
    @get:Rule
    val schedulerRule = RxSchedulerRule()

    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    private val repository: MarvelRepository = mockk()
    lateinit var viewModel: MarvelDetailViewModel

    @Before
    fun setUp() {
        viewModel = MarvelDetailViewModel(repository)
    }

    @Test
    fun `specific character detail are retrieved successfully`() {
        // Given
        val id = "1234"
        val subject = SingleSubject.create<CharacterModel>()
        val model = mockCharacter()
        every { repository.getMarvelCharacterDetail(id) } returns subject

        // When
        viewModel.getCharacterDetail(id)

        // Then
        verify(exactly = 1) { repository.getMarvelCharacterDetail(id) }
        viewModel.loading.observeOnce { assert(it) }
        subject.onSuccess(model)
        viewModel.loading.observeOnce { assert(!it) }
        viewModel.marvelCharacterDetail.observeOnce {
            assert(it.id == model.id)
        }
    }

    @Test
    fun `specific character detail are not retrieved due to an error`() {
        // Given
        val id = "1234"
        val subject = SingleSubject.create<CharacterModel>()
        every { repository.getMarvelCharacterDetail(id) } returns subject

        // When
        viewModel.getCharacterDetail(id)

        // Then
        verify(exactly = 1) { repository.getMarvelCharacterDetail(id) }
        viewModel.loading.observeOnce { assert(it) }
        subject.onError(Throwable())
        viewModel.loading.observeOnce { assert(!it) }
        viewModel.error.observeOnce { assert(it is UnexpectedError) }
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