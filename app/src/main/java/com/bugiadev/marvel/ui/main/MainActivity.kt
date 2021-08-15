package com.bugiadev.marvel.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigator
import com.bugiadev.marvel.R
import com.bugiadev.marvel.databinding.ActivityMainBinding
import com.bugiadev.marvel.di.DaggerMarvelComponent
import com.bugiadev.marvel.di.MarvelComponent
import com.bugiadev.marvel.di.MarvelInjection
import com.bugiadev.marvel.utils.injector
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity(), MarvelInjection {
    private var _binding: ActivityMainBinding? = null
    private var binding: ActivityMainBinding
        get() = _binding
            ?: throw IllegalStateException("Binding is null, check the status of your Activity ${this::class}")
        set(value) {
            _binding = value
        }

    private lateinit var marvelComponent: MarvelComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        marvelComponent = DaggerMarvelComponent.factory().create(injector)
    }

    override fun getMarvelComponent(): MarvelComponent = marvelComponent
}