package com.example.news.ui.main_screen

sealed class StateStatusBar {

    object FullScreen : StateStatusBar()

    object NotFullScreen : StateStatusBar()
}
