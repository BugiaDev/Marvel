package com.bugiadev.data

import com.bugiadev.data.datasource.MarvelDataSource
import com.bugiadev.data.entities.CharacterEntity
import com.bugiadev.data.entities.MarvelDataEntity
import com.bugiadev.data.entities.MarvelEntity
import com.bugiadev.data.entities.ThumbnailEntity
import com.bugiadev.data.repository.MarvelRepositoryImpl
import com.bugiadev.domain.repository.MarvelRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class MarvelRepositoryImplTest {
    private val dataSource: MarvelDataSource = mockk()
    private lateinit var repository: MarvelRepository

    @Before
    fun setUp() {
        repository = MarvelRepositoryImpl(dataSource = dataSource)
    }

    @Test
    fun `characters list are retrieved successfully`() {
        // Given
        val data = mockMarvelEntity()
        every { dataSource.getCharacters() } returns Single.just(data)

        // When
        val sut = repository.getMarvelCharactersList().test()

        // Then
        verify(exactly = 1) { dataSource.getCharacters() }
        sut.assertComplete()
        sut.assertNoErrors()
        sut.assertValue { list ->
            list[0].id == mockCharacter().id
        }
    }

    @Test
    fun `specific character detail retrieved successfully`() {
        // Given
        val data = mockMarvelEntity()
        val characterId = "1234"
        every { dataSource.getCharacterDetail(characterId) } returns Single.just(data)

        // When
        val sut = repository.getMarvelCharacterDetail(characterId).test()

        // Then
        verify(exactly = 1) { dataSource.getCharacterDetail(characterId) }
        sut.assertComplete()
        sut.assertNoErrors()
        sut.assertValue { character ->
            character.id == mockCharacter().id
        }
    }

    // region MOCKS
    private fun mockMarvelEntity() = MarvelEntity(
        data = MarvelDataEntity(
            total = 1,
            count = 1,
            characters = mockCharacterList()
        )
    )

    private fun mockCharacterList() = listOf(mockCharacter())

    private fun mockCharacter() = CharacterEntity(
        id = 1234,
        name = "super name",
        description = "super description",
        modified = "21/07/2021 14:33",
        resourceURI = "http://marvel.hero.com/1234",
        thumbnail = ThumbnailEntity(
            path = "http://image.path.com/1234",
            extension = "jpg"
        )
    )
    // endregion
}