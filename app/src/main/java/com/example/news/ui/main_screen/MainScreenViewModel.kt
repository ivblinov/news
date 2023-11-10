package com.example.news.ui.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.Repository
import com.example.news.data.room.Article
import com.example.news.data.room.ArticleDao
import com.example.news.domain.App
import com.example.news.ui.news_screen.ArticleParcel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch

private const val TAG = "MyLog"
object MainScreenViewModel : ViewModel() {

    private val articleDao = App.instance.db.articleDao()
    private val repository = Repository()

    private val _state = MutableStateFlow<StateBottomNav>(StateBottomNav.Initial)
    val state = _state.asStateFlow()

    private val _statusBarState = MutableStateFlow<StateStatusBar>(StateStatusBar.NotFullScreen)
    val statusBarState = _statusBarState.asStateFlow()

    private val _savedStatus = MutableStateFlow<StateIconSave>(StateIconSave.NotSaved)
    val savedStatus = _savedStatus.asStateFlow()

    fun clickedHeadlinesButton() {
        viewModelScope.launch {
            _state.value = StateBottomNav.Headlines
        }
    }

    fun clickedSavedButton() {
        viewModelScope.launch {
            _state.value = StateBottomNav.Saved
        }
    }

    fun clickedSourcesButton() {
        viewModelScope.launch {
            _state.value = StateBottomNav.Sources
        }
    }

    fun openedNewsScreen() {
        viewModelScope.launch {
            _statusBarState.value = StateStatusBar.FullScreen
        }
    }

    fun closedNewsScreen() {
        viewModelScope.launch {
            _statusBarState.value = StateStatusBar.NotFullScreen
        }
    }

    fun clickedOnNavigateBack() {
        viewModelScope.launch {
            _statusBarState.value = StateStatusBar.CameBack
        }
    }

    fun clickedOnSaveNews() {
        viewModelScope.launch {
            _savedStatus.value = StateIconSave.Saved
        }
    }

    fun saveNews(news: ArticleParcel, articleId: Int?) {
        viewModelScope.launch {
            repository.saveNews(news, articleId)
            _savedStatus.value = StateIconSave.NotSaved
        }
    }

    val allArticles = this.articleDao.getAll()
}