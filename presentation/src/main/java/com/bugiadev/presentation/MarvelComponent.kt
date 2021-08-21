package com.bugiadev.presentation

import com.bugiadev.di.ApplicationComponent
import com.bugiadev.di.annotations.ActivityScope
import com.bugiadev.presentation.fragment.DetailFragment
import com.bugiadev.presentation.fragment.ListFragment
import com.bugiadev.presentation.viewmodel.MarvelDetailViewModel
import com.bugiadev.presentation.viewmodel.MarvelListViewModel
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class]
)
interface MarvelComponent {
    val marvelListViewModel: MarvelListViewModel
    val marvelDetailViewModel: MarvelDetailViewModel

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