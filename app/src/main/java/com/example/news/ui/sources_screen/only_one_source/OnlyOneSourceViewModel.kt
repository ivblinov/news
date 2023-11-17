package com.example.news.ui.sources_screen.only_one_source

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import com.example.news.data.retrofit_one_source.OneSource
import com.example.news.data.retrofit_one_source.OneSourceService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MyLog"
object OnlyOneSourceViewModel : ViewModel() {
    private var pageSize = 12
    private var page = 1
    var oldArticlesList = mutableListOf<OneSource.Article>()
    var newArticlesList = mutableListOf<OneSource.Article>()


    var isButtonBackActive = false

    private val _oneSourceState = MutableStateFlow<OneSourceState>(OneSourceState.Request)
    val oneSourceState = _oneSourceState.asStateFlow()

    suspend fun getSources(idSource: String, pageSize: Int = 12, page: Int = 1): MutableList<OneSource.Article> {
        val source = OneSourceService.oneSourceApi.getSourceList(sources = idSource, pageSize = pageSize, page = page)
        oldArticlesList.addAll(source.articles)
        newArticlesList.addAll(source.articles)
        _oneSourceState.value = OneSourceState.Received
        return source.articles
    }

    fun changeStateReceived() {
        _oneSourceState.value = OneSourceState.Received
    }

    fun getExtraArticles(idSource: String) {
        viewModelScope.launch {
            val source = OneSourceService.oneSourceApi.getSourceList(sources = idSource, pageSize = pageSize, page = ++page)
            newArticlesList.addAll(source.articles)
            _oneSourceState.value = OneSourceState.Reload
        }
    }

    fun unChangeOneSourceState() {
        _oneSourceState.value = OneSourceState.Request
    }


    private val _buttonAppBackState = MutableStateFlow<ButtonBackState>(ButtonBackState.Initial)
    val buttonAppBackState = _buttonAppBackState.asStateFlow()

    fun changeButtonVisible() {
        _buttonAppBackState.value = ButtonBackState.Active
    }

    fun changeButtonGone() {
        _buttonAppBackState.value = ButtonBackState.InActive
    }

    fun updateList(): DiffUtil.DiffResult {
        return DiffUtil.calculateDiff(
            OneSourceDiffUtilCallBack(
                oldArticleList = oldArticlesList,
                newArticleList = newArticlesList
            )
        )
    }
}