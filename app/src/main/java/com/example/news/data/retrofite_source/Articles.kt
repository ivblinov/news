package com.example.news.data.retrofite_source

import com.google.gson.annotations.SerializedName

data class Articles(
    @SerializedName("status") val status: String,
    @SerializedName("sources") val sources: MutableList<Article>
)

data class Article(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    @SerializedName("category") val category: String,
    @SerializedName("country") val country: String
)
