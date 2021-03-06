package com.example.mnewsapp.networks

import com.example.mnewsapp.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    fun getTopArticles(@Query("country") country: String) : Call<TopNewsResponse>

    @GET("top-headlines")
    fun getArticlesByCategory(@Query("category") category: String) : Call<TopNewsResponse>

    @GET("everything")
    fun getArticlesBySources(@Query("sources") source: String): Call<TopNewsResponse>

    @GET("everything")
    fun getArticleBySearch(@Query("q") query: String): Call<TopNewsResponse>
}