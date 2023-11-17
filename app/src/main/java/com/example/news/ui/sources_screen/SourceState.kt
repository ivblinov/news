package com.example.news.ui.sources_screen

sealed class SourceState {
    object Request : SourceState()
    object Received : SourceState()
}
