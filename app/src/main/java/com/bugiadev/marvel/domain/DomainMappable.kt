package com.bugiadev.marvel.domain

interface DomainMappable<R> {
    fun toDomain(): R
}