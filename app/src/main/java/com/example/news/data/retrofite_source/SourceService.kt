package com.example.news.data.retrofite_source

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://newsapi.org/v2/"

const val api_0 = "76e65516d700446cbba1ec05b084148f"
const val api_1 = "755f011367234479a6fc87c4fbd87e59"
const val api_2 = "a9201dcf799849e18275d09222d2aafa"

object SourceService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val sourceApi: SourceApi = retrofit.create(
        SourceApi::class.java
    )
}

interface SourceApi {
    @GET("top-headlines/sources?country=us&apiKey=$api_2")
    suspend fun getSourceList(): Articles
}