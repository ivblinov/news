package com.example.news.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class Article(
    @PrimaryKey
    val id: Int,
    val name: String,
    val title: String,
    val content: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String
)
