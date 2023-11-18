package com.example.news.ui.search_screen

sealed class SearchState {
    object InActive : SearchState()
    object Active : SearchState()
    object Request : SearchState()
    object Received : SearchState()
}
