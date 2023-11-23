package com.example.news.ui.splash_screen

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.WindowCompat
import com.example.news.R
import com.example.news.databinding.ActivitySplashScreenBinding
import com.example.news.domain.App
import com.example.news.domain.Screens
import com.github.terrakok.cicerone.androidx.AppNavigator

private const val TAG = "MyLog"
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val navigator = AppNavigator(this, R.id.splash_screen)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lottieView.playAnimation()
        binding.lottieView.addAnimatorUpdateListener {
            if (it.animatedValue as Float > 0.80) {
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

    private fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }
}