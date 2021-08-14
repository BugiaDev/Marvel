package com.bugiadev.marvel.di.modules

import com.bugiadev.marvel.data.datasource.MarvelDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
object MarvelModule {
    @JvmStatic
    @Provides
    fun providesMarvelDataSource(@Named("commonRetrofit") retrofit: Retrofit): MarvelDataSource =
        retrofit.create(MarvelDataSource::class.java)
}