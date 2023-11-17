package com.example.news.ui.sources_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.news.data.retrofite_source.Article
import com.example.news.data.retrofite_source.SourceService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "MyLog"
object SourcesViewModel : ViewModel() {

    private val _sourceStatus = MutableStateFlow<SourceState>(SourceState.Request)
    val sourceState = _sourceStatus.asStateFlow()

    suspend fun getSources(): MutableList<Article> {
        val source = SourceService.sourceApi.getSourceList()
        _sourceStatus.value = SourceState.Received
        return source.sources
    }

    fun changeSourceStatusOnRequest() {
        _sourceStatus.value = SourceState.Request
    }
}