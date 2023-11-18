package com.example.news.ui.search_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object SearchViewModel : ViewModel() {

    var category: String = "general"

    private val _searchState = MutableStateFlow<SearchState>(SearchState.InActive)
    val searchState = _searchState.asStateFlow()

    fun changeStateActive() {
        _searchState.value = SearchState.Active
    }
}