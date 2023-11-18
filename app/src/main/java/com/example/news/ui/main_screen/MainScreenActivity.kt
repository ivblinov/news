package com.example.news.ui.main_screen

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.news.R
import com.example.news.databinding.ActivityMainScreenBinding
import com.example.news.domain.App
import com.example.news.domain.Screens
import com.example.news.ui.search_screen.SearchState
import com.example.news.ui.search_screen.SearchViewModel
import com.example.news.ui.sources_screen.SourceRcAdapter
import com.example.news.ui.sources_screen.SourcesViewModel
import com.example.news.ui.sources_screen.only_one_source.ButtonBackState
import com.example.news.ui.sources_screen.only_one_source.OneSourceState
import com.example.news.ui.sources_screen.only_one_source.OnlyOneSourceViewModel
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.material.search.SearchView
import kotlinx.coroutines.launch

private const val TAG = "MyLog"

class MainScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainScreenBinding
    private val navigator = AppNavigator(this, R.id.fragment_container)
    private val viewModel: MainScreenViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        removeOldNews()

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.button_headlines ->
                    viewModel.clickedButtonOnBottomNav(R.string.headlines, Screens.headlinesFragment())
                R.id.button_saved ->
                    viewModel.clickedButtonOnBottomNav(R.string.saved, Screens.savedFragment())
                R.id.button_sources ->
                    viewModel.clickedButtonOnBottomNav(R.string.sources, Screens.sourcesFragment())
            }
            true
        }

        binding.buttonBack.setOnClickListener {
            viewModel.clickedOnNavigateBack()
        }

        binding.buttonAppBack.setOnClickListener {
            OnlyOneSourceViewModel.changeButtonGone()
        }

        binding.btnTextClean.setOnClickListener {
            binding.searchField.text.clear()
        }

        binding.btnSearchExit.setOnClickListener {
            Log.d(TAG, "exit")
        }

        binding.searchField.addTextChangedListener {
            Log.d(TAG, it.toString())
        }

        binding.topAppBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.search) {
                SearchViewModel.changeStateActive()
            }
            if (it.itemId == R.id.filter) {
                Log.d(TAG, "clickedFilter")
            }
            true
        }

        fun activeSearchBar() {
            binding.searchBar.visibility = View.VISIBLE
            binding.searchBar.isActivated = true
            binding.appBarLayout.visibility = View.INVISIBLE
            binding.appBarLayout.isActivated = false
        }

        binding.topAppBarNewsScreen.setOnMenuItemClickListener {
            if(it.itemId == R.id.save) {
                viewModel.clickedOnSaveNews()
            }
            true
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                SearchViewModel.searchState.collect { searchState ->
                    when (searchState) {
                        SearchState.InActive -> {
                        }
                        SearchState.Active -> {
                            activeSearchBar()
                        }
                        SearchState.Request -> {

                        }
                        SearchState.Received -> {

                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        StateBottomNav.Clicked -> {
                            Log.d(TAG, "${viewModel.titleAppBar}")
                            clickedButtonOnBottomNav(
                                    viewModel.titleAppBar,
                                    viewModel.screenBottomNav
                                )
                            viewModel.setUnClickedState()
                        }
                        StateBottomNav.UnClicked -> {
                        }
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.titleAppBarState.collect { titleAppBarState ->
                    when (titleAppBarState) {
                        StateBottomNav.Clicked -> {
                            viewModel.titleAppBarString.lastOrNull()?.let { changeTitleAppBar(it) }
                        }
                        StateBottomNav.UnClicked -> {
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                OnlyOneSourceViewModel.buttonAppBackState.collect { buttonAppBackState ->
                    when (buttonAppBackState) {

                        ButtonBackState.Initial -> {
                        }
                        ButtonBackState.Active -> {
                            visibleButtonAppBack()
                        }
                        ButtonBackState.InActive -> {
                            goneButtonAppBack()
//                            viewModel.clickedButtonOnBottomNav(R.string.sources, Screens.sourcesFragment())
//                            viewModel.titleAppBarString.removeLastOrNull()
//                            App.instance.router.backTo(Screens.sourcesFragment())

                            exit()
//                            SourcesViewModel.changeSourceStatusOnRequest()
                        }
                    }
                }
            }
        }
    }

    private fun clickedButtonOnBottomNav(title: Int, screen: Screen) {
        newRootScreen(screen)
        binding.topAppBar.setTitle(title)
    }

    private fun goneButtonAppBack() {
        binding.buttonAppBack.visibility = View.GONE
    }

    private fun visibleButtonAppBack() {
        binding.buttonAppBack.visibility = View.VISIBLE
    }

    private fun changeTitleAppBar(title: String) {
        binding.topAppBar.title = title
        MainScreenViewModel.unChangeTitleAppBar()
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun removeOldNews() {
        MainScreenViewModel.removeOldNews()
    }

    private fun navigateTo(screen: Screen) {
        App.instance.router.navigateTo(screen = screen)
    }

    fun getObj() {
        SearchViewModel.category = "a"
    }
}