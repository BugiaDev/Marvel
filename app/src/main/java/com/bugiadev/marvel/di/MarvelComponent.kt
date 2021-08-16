package com.bugiadev.marvel.di

import com.bugiadev.marvel.di.annotations.ActivityScope
import com.bugiadev.marvel.ui.fragment.DetailFragment
import com.bugiadev.marvel.ui.fragment.ListFragment
import com.bugiadev.marvel.ui.viewmodel.MarvelViewModel
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class]
)
interface MarvelComponent {
    val marvelViewModel: MarvelViewModel

    fun inject(fragment: ListFragment)
    fun inject(fragment: DetailFragment)

    @Component.Factory
    interface Factory {
        fun create(
            applicationComponent: ApplicationComponent
        ): MarvelComponent
    }
}

interface MarvelInjection {
    fun getMarvelComponent(): MarvelComponent
}