package com.bugiadev.marvel.di.modules

import com.bugiadev.marvel.data.repository.MarvelRepositoryImpl
import com.bugiadev.marvel.di.annotations.ApplicationScope
import com.bugiadev.marvel.domain.repository.MarvelRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @ApplicationScope
    @Binds
    abstract fun bindsMarvelRepository(implementation: MarvelRepositoryImpl): MarvelRepository
}