package com.example.news.ui.main_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MyLog"
object MainScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow<StateBottomNav>(StateBottomNav.Initial)
    val state = _state.asStateFlow()

    fun clickedHeadlinesButton() {
        viewModelScope.launch {
            _state.value = StateBottomNav.Headlines
        }
    }

    fun clickedSavedButton() {
        viewModelScope.launch {
            _state.value = StateBottomNav.Saved
        }
    }

    fun clickedSourcesButton() {
        viewModelScope.launch {
            _state.value = StateBottomNav.Sources
        }
    }

    private val _statusBarState = MutableStateFlow<StateStatusBar>(StateStatusBar.NotFullScreen)
    val statusBarState = _statusBarState.asStateFlow()

    fun openedNewsScreen() {
        viewModelScope.launch {
            _statusBarState.value = StateStatusBar.FullScreen
        }
    }

    fun closedNewsScreen() {
        viewModelScope.launch {
            _statusBarState.value = StateStatusBar.NotFullScreen
        }
    }

}