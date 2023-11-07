package com.example.news.data

import android.util.Log
import com.example.news.data.room.Article
import com.example.news.domain.App
import com.example.news.ui.news_screen.ArticleParcel

private const val TAG = "MyLog"
class Repository {

    var id: Int = 0
    private val articleDao = App.instance.db.articleDao()

    suspend fun saveNews(news: ArticleParcel) {
        Log.d(TAG, "saveNews: ")
        articleDao.insert(
            Article(
                id = ++id,
                name = news.name,
                title = news.title,
                content = news.content,
                url = news.url,
                urlToImage = news.urlToImage,
                publishedAt = news.publishedAt
            )
        )
    }
}