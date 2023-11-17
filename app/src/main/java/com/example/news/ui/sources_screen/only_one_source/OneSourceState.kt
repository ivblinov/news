package com.example.news.ui.sources_screen.only_one_source

sealed class OneSourceState {
    object Request : OneSourceState()
    object Received : OneSourceState()
    object Reload : OneSourceState()
}
