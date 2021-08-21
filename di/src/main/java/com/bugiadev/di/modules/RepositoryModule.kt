package com.bugiadev.di.modules

import com.bugiadev.data.repository.MarvelRepositoryImpl
import com.bugiadev.di.annotations.ApplicationScope
import com.bugiadev.domain.repository.MarvelRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @ApplicationScope
    @Binds
    abstract fun bindsMarvelRepository(implementation: MarvelRepositoryImpl): MarvelRepository
}