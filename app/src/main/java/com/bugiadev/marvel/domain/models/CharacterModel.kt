package com.bugiadev.marvel.domain.models

data class CharacterModel(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    val characterImage: ThumbnailModel?
)

data class ThumbnailModel(
    val path: String?,
    val extension: String?
)