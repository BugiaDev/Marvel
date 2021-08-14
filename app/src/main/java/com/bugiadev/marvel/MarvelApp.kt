package com.bugiadev.marvel

import android.app.Application
import com.bugiadev.marvel.di.ApplicationComponent
import com.bugiadev.marvel.di.DaggerApplicationComponent
import com.bugiadev.marvel.di.DaggerMarvelComponent
import com.bugiadev.marvel.di.MarvelComponent

interface ApplicationComponentProvider {
    fun provideApplicationComponent(): ApplicationComponent
}

class MarvelApp: Application(), ApplicationComponentProvider {
    private lateinit var applicationComponent: ApplicationComponent
    private lateinit var marvelComponent: MarvelComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(applicationContext)
        marvelComponent = DaggerMarvelComponent.factory().create(applicationComponent)
    }

    override fun provideApplicationComponent(): ApplicationComponent = applicationComponent
}