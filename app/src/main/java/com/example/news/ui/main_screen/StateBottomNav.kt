package com.example.news.ui.main_screen

sealed class StateBottomNav {
    object Initial : StateBottomNav()
    object Headlines : StateBottomNav()
    object Saved : StateBottomNav()
    object Sources : StateBottomNav()
}
