package com.bugiadev.marvel.ui.presentation

import com.bugiadev.marvel.domain.models.CharacterModel

data class CharacterDisplay(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?
)

fun CharacterModel.toDisplay() : CharacterDisplay =
    CharacterDisplay(
        id = id,
        name = name,
        description = description,
        modified = modified,
        resourceURI = resourceURI
    )