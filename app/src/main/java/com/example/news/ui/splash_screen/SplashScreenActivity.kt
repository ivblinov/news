package com.example.news.ui.splash_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.news.R
import com.example.news.databinding.ActivitySplashScreenBinding
import com.example.news.domain.App
import com.example.news.domain.Screens
import com.github.terrakok.cicerone.androidx.AppNavigator


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val navigator = AppNavigator(this, R.id.splash_screen)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lottieView.playAnimation()
        binding.lottieView.addAnimatorUpdateListener {
            if (it.animatedValue as Float > 0.99) {     // изменить на 0.99
                binding.lottieView.cancelAnimation()
                App.instance.router.newRootScreen(Screens.mainScreen(this))
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.instance.navigatorHolder.removeNavigator()
        super.onPause()
    }
}