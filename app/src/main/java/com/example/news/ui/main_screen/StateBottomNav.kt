package com.example.news.ui.main_screen

sealed class StateBottomNav {
    object Clicked : StateBottomNav()
    object UnClicked : StateBottomNav()

}
