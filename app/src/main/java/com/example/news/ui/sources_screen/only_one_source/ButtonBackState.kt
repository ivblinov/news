package com.example.news.ui.sources_screen.only_one_source

sealed class ButtonBackState {

    object Initial : ButtonBackState()
    object Active : ButtonBackState()
    object InActive : ButtonBackState()
}
