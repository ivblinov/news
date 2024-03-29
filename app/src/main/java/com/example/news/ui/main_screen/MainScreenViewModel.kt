package com.example.news.ui.main_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.R
import com.example.news.data.Repository
import com.example.news.data.room.Article
import com.example.news.domain.App
import com.example.news.domain.Screens
import com.example.news.ui.news_screen.ArticleParcel
import com.github.terrakok.cicerone.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

private const val TAG = "MyLog"
private const val IS_OLD_NEWS = 14
object MainScreenViewModel : ViewModel() {
    var titleAppBar = R.string.headlines
    var screenBottomNav: Screen = Screens.headlinesFragment()

    var titleAppBarString = mutableListOf("")

    private val articleDao = App.instance.db.articleDao()
    private val repository = Repository()

    private val _state = MutableStateFlow<StateBottomNav>(StateBottomNav.Clicked)
    val state = _state.asStateFlow()

    private val _statusBarState = MutableStateFlow<StateStatusBar>(StateStatusBar.NotFullScreen)
    val statusBarState = _statusBarState.asStateFlow()

    private val _savedStatus = MutableStateFlow<StateIconSave>(StateIconSave.NotSaved)
    val savedStatus = _savedStatus.asStateFlow()

    private val _titleAppBarState = MutableStateFlow<StateBottomNav>(StateBottomNav.UnClicked)
    val titleAppBarState = _titleAppBarState.asStateFlow()

    fun clickedButtonOnBottomNav(title: Int = titleAppBar, screen: Screen = screenBottomNav) {
        if (titleAppBar == title) return

        viewModelScope.launch {
            _state.value = StateBottomNav.Clicked
            titleAppBar = title
            screenBottomNav = screen
        }
    }

    fun setUnClickedState() {
        _state.value = StateBottomNav.UnClicked
    }

    fun changeTitleAppBar(title: String) {
        if (titleAppBarString.lastOrNull() != title) {
            titleAppBarString.add(title)
            viewModelScope.launch {
                _titleAppBarState.value = StateBottomNav.Clicked
            }
        }
    }

    fun unChangeTitleAppBar() {
        _titleAppBarState.value = StateBottomNav.UnClicked
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

    fun removeOldNews() {
        viewModelScope.launch { getOldNewsList()
            .forEach { articleDao.delete(it) } }
    }

    private suspend fun getOldNewsList(): List<Article> {
        val articleList = articleDao.getArticleList()
        return articleList.filter { getOldNews(it.publishedAt) > IS_OLD_NEWS }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getOldNews(dataPublished: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'")
        val publishedAt = dateFormat.parse(dataPublished)
        val today = Calendar.getInstance().time
        val resultDay = today.time - (publishedAt?.time ?: 0)
        val dateFormatForDay = SimpleDateFormat("ddd")
        return dateFormatForDay.format(resultDay).toInt()
    }

    val allArticles = this.articleDao.getAll()
}