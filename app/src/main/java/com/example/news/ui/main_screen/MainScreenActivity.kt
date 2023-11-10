package com.example.news.ui.main_screen

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
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
            }
            true
        }

        binding.buttonBack.setOnClickListener {
            viewModel.clickedOnNavigateBack()
        }

        binding.topAppBarNewsScreen.setOnMenuItemClickListener {
            if(it.itemId == R.id.save) {
                viewModel.clickedOnSaveNews()
            }
            true
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        StateBottomNav.Initial -> {
                            newRootScreen(Screens.headlinesFragment())
                            binding.topAppBar.setTitle(R.string.headlines)
                        }
                        StateBottomNav.Headlines -> {
                            newRootScreen(Screens.headlinesFragment())
                            binding.topAppBar.setTitle(R.string.headlines)
                        }
                        StateBottomNav.Saved -> {
                            newRootScreen(Screens.savedFragment())
                            binding.topAppBar.setTitle(R.string.saved)
                        }
                        StateBottomNav.Sources -> {
                            newRootScreen(Screens.sourcesFragment())
                            binding.topAppBar.setTitle(R.string.sources)
                        }
                        else -> Log.d(TAG, "onCreate: ")
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.statusBarState.collect { statusBarState ->
                    when (statusBarState) {
                        StateStatusBar.FullScreen -> {
                            hideStatusBar()
                        }
                        StateStatusBar.NotFullScreen -> {
                            showStatusBar()
                        }
                        StateStatusBar.CameBack -> {
                            exit()
                        }
                    }
                }
            }
        }
    }

    private fun hideStatusBar() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        with(binding) {
            appBarLayout.visibility = View.GONE
            appBarLayoutNewsScreen.visibility = View.VISIBLE
            buttonBack.visibility = View.VISIBLE
        }
        setStatusBarColor("#00000000")
    }

    private fun showStatusBar() {
        WindowCompat.setDecorFitsSystemWindows(window, true)

        with(binding) {
            appBarLayout.visibility = View.VISIBLE
            appBarLayoutNewsScreen.visibility = View.GONE
            buttonBack.visibility = View.GONE
        }
        setStatusBarColor("#009EBD")
    }

    private fun setStatusBarColor(color: String) {
        window.statusBarColor = Color.parseColor(color)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.instance.navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun exit() {
        App.instance.router.exit()
    }

    private fun newRootScreen(screen: Screen) {
        App.instance.router.newRootScreen(screen = screen)
    }

    private fun navigateTo(screen: Screen) {
        App.instance.router.navigateTo(screen = screen)
    }
}