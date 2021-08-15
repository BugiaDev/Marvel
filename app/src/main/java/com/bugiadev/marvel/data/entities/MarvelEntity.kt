package com.bugiadev.marvel.data.entities

import com.bugiadev.marvel.domain.DomainMappable
import com.bugiadev.marvel.domain.models.CharacterModel
import com.bugiadev.marvel.utils.empty
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarvelEntity(
    @Json(name = "data") val data: MarvelDataEntity
)

@JsonClass(generateAdapter = true)
data class MarvelDataEntity(
    @Json(name = "total") val total: Int? = Int.empty(),
    @Json(name = "count") val count: Int? = Int.empty(),
    @Json(name = "results") val characters: List<CharacterEntity>? = emptyList()
)

@JsonClass(generateAdapter = true)
data class CharacterEntity(
    @Json(name = "id") val id: Int? = Int.empty(),
    @Json(name = "name") val name: String? = String.empty(),
    @Json(name = "description") val description: String? = String.empty(),
    @Json(name = "modified") val modified: String? = String.empty(),
    @Json(name = "resourceURI") val resourceURI: String? = String.empty()
) : DomainMappable<CharacterModel> {
    override fun toDomain(): CharacterModel = CharacterModel(
        id = id,
        name = name,
        description = description,
        modified = modified,
        resourceURI = resourceURI,
        characterImage = null
    )
}