package com.example.news.data.retrofit_one_source

import com.google.gson.annotations.SerializedName

data class OneSource(
    @SerializedName("totalResults")
    val totalResults: Int = 0,

    @SerializedName("articles")
    val articles: MutableList<Article>

) {
    class Article (
        @SerializedName("source")
        val source: Source,

        @SerializedName("title")
        val title: String,

        @SerializedName("url")
        val url: String,

        @SerializedName("urlToImage")
        val urlToImage: String,

        @SerializedName("publishedAt")
        val publishedAt: String,

        @SerializedName("content")
        val content: String
    )

    class Source (
        @SerializedName("name")
        val name: String
    )
}