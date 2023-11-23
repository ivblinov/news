package com.example.news.data

import com.example.news.data.room.Article
import com.example.news.domain.App
import com.example.news.ui.news_screen.ArticleParcel

class Repository {

    var id: Int = 0
    private val articleDao = App.instance.db.articleDao()

    suspend fun saveNews(news: ArticleParcel, articleId: Int?) {
        if (articleId != null) {
            id = articleId
        }
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