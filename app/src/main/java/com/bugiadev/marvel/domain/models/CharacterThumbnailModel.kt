package com.bugiadev.marvel.domain.models

data class CharacterThumbnailModel(val path: String?, val extension: String?, val image: String?) {
    constructor(path: String, extension: String) : this(path, extension, null)
    constructor(image: String) : this(null, null, image)

    fun image() = "$path.$extension"

    companion object {
        fun thumbail(image: String): CharacterThumbnailModel {
            val imageSplit = image.split(".")
            return CharacterThumbnailModel(imageSplit[0], imageSplit[1])
        }
    }
}