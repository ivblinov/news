package com.example.news.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article")
    fun getAll(): Flow<List<Article>>

    @Query("SELECT * FROM article")
    suspend fun getArticleList(): List<Article>

    @Insert
    suspend fun insert(article: Article)

    @Delete
    suspend fun delete(article: Article)
}