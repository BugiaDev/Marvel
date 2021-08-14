package com.bugiadev.marvel.di

import com.bugiadev.marvel.di.annotations.ActivityScope
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class]
)
interface MarvelComponent {
    /*
    todo

        val churchViewModel: ChurchViewModel

    fun inject(fragment: ChurchListFragment)
    fun inject(fragment: TempleListFragment)
     */
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