package com.bugiadev.marvel

import android.app.Application
import com.bugiadev.di.ApplicationComponent
import com.bugiadev.di.DaggerApplicationComponent
import com.bugiadev.presentation.DaggerMarvelComponent
import com.bugiadev.presentation.MarvelComponent
import com.bugiadev.presentation.ApplicationComponentProvider

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