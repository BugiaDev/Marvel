package com.bugiadev.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bugiadev.presentation.DaggerMarvelComponent
import com.bugiadev.presentation.MarvelComponent
import com.bugiadev.presentation.MarvelInjection
import com.bugiadev.presentation.utils.injector
import com.bugiadev.presentation.R
import com.bugiadev.presentation.databinding.ActivityMainBinding
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
        window.navigationBarColor = getColor(R.color.very_dark_red)
        _binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        marvelComponent = DaggerMarvelComponent.factory().create(injector)
    }

    override fun getMarvelComponent(): MarvelComponent = marvelComponent
}