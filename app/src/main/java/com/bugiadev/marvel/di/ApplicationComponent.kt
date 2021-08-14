package com.bugiadev.marvel.di

import android.content.Context
import com.bugiadev.marvel.di.annotations.ApplicationScope
import com.bugiadev.marvel.di.modules.MarvelModule
import com.bugiadev.marvel.di.modules.NetworkModule
import com.bugiadev.marvel.di.modules.RepositoryModule
import com.bugiadev.marvel.domain.repository.MarvelRepository
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named

@ApplicationScope
@Component(modules = [NetworkModule::class, RepositoryModule::class, MarvelModule::class])
interface ApplicationComponent {
    val okHttpClient: OkHttpClient
    val converter: Converter.Factory
    val adapter: CallAdapter.Factory
    val context: Context
    val marvelRepository: MarvelRepository

    @Named("commonRetrofit")
    fun commonRetrofit(): Retrofit

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context
        ): ApplicationComponent
    }
}