package com.bugiadev.data.entities

import com.bugiadev.data.utils.empty
import com.bugiadev.domain.DomainMappable
import com.bugiadev.domain.models.CharacterModel
import com.bugiadev.domain.models.ThumbnailModel
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
    @Json(name = "resourceURI") val resourceURI: String? = String.empty(),
    @Json(name = "thumbnail") val thumbnail: ThumbnailEntity?
) : DomainMappable<CharacterModel> {
    override fun toDomain(): CharacterModel = CharacterModel(
        id = id,
        name = name,
        description = description,
        modified = modified,
        resourceURI = resourceURI,
        characterImage = thumbnail?.toDomain()
    )
}

@JsonClass(generateAdapter = true)
data class ThumbnailEntity(
    @Json(name = "path") val path: String? = String.empty(),
    @Json(name = "extension") val extension: String? = String.empty()
) : DomainMappable<ThumbnailModel> {
    override fun toDomain(): ThumbnailModel = ThumbnailModel(
        path = path,
        extension = extension
    )
}