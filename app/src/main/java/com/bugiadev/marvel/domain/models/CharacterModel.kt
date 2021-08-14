package com.bugiadev.marvel.domain.models

data class CharacterModel(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    val characterImage: CharacterThumbnailModel?
) {
    companion object {
        fun thumbnail(image: String): CharacterThumbnailModel {
            val imageSplit = image.split(".")
            return CharacterThumbnailModel(imageSplit[0], imageSplit[1])
        }
    }
}