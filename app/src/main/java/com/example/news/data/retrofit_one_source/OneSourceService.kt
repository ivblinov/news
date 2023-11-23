package com.example.news.data.retrofit_one_source

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/v2/"

const val api_0 = "76e65516d700446cbba1ec05b084148f"
const val api_1 = "755f011367234479a6fc87c4fbd87e59"
const val api_2 = "a9201dcf799849e18275d09222d2aafa"

object OneSourceService {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val oneSourceApi: OneSourceApi = retrofit.create(
        OneSourceApi::class.java
    )
}

interface OneSourceApi {
    @Headers("X-Api-Key: $api_2")
    @GET("everything")
    suspend fun getSourceList(
        @Query("sources") sources: String = "bbc-news",
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int = 1
    ): OneSource
}