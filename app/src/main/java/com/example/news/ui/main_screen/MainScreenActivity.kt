package com.example.news.ui.main_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.news.R
import com.example.news.databinding.ActivityMainScreenBinding
import com.example.news.domain.App
import com.example.news.domain.Screens
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.launch

private const val TAG = "MyLog"

class MainScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    private val navigator = AppNavigator(this, R.id.fragment_container)
    private val viewModel: MainScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.button_headlines -> {viewModel.clickedHeadlinesButton()}
                R.id.button_saved -> {viewModel.clickedSavedButton()}
                R.id.button_sources -> { viewModel.clickedSourcesButton() }

//                R.id.button_headlines -> {newRootScreen(Screens.HeadlinesFragment())}
//                R.id.button_saved -> {newRootScreen(Screens.SavedFragment())}
            }
            true
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        StateBottomNav.Initial -> { newRootScreen(Screens.headlinesFragment()) }
                        StateBottomNav.Headlines -> { newRootScreen(Screens.headlinesFragment()) }
                        StateBottomNav.Saved -> { newRootScreen(Screens.savedFragment()) }
                        StateBottomNav.Sources -> { newRootScreen(Screens.sourcesFragment()) }
                        else -> Log.d(TAG, "onCreate: ")
                    }
                }
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

    private fun newRootScreen(screen: Screen) {
        App.instance.router.newRootScreen(screen = screen)
    }

    private fun navigateTo(screen: Screen) {
        App.instance.router.navigateTo(screen = screen)
    }
}