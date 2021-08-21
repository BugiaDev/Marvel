package com.bugiadev.presentation.display

import com.bugiadev.domain.models.CharacterModel

data class CharacterDisplay(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    val imageURI: String?
)

fun CharacterModel.toDisplay(): CharacterDisplay {
    val imageURI = characterImage?.path + "." + characterImage?.extension
    return CharacterDisplay(
        id = id,
        name = name,
        description = description,
        modified = modified,
        resourceURI = resourceURI,
        imageURI = imageURI
    )
}
