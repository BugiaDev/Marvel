package com.bugiadev.presentation.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bugiadev.presentation.R
import com.bugiadev.presentation.databinding.ActivitySplashBinding
import com.bugiadev.presentation.main.MainActivity
import java.lang.IllegalStateException

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private var binding: ActivitySplashBinding
        get() = _binding
            ?: throw IllegalStateException("Binding is null, check the status of your Activity ${this::class}")
        set(value) {
            _binding = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_splash
        )

        binding.splashAnimation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                //Do nothing
            }

            override fun onAnimationEnd(animation: Animator?) {
                Intent(applicationContext, MainActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }

            override fun onAnimationCancel(animation: Animator?) {
                //Do nothing
            }

            override fun onAnimationStart(animation: Animator?) {
                //Do nothing
            }

        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}