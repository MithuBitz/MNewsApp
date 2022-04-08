package com.example.mnewsapp.models

data class TopNewsResponse(
    val status: String? = null,
    val totalResult: Int? = null,
    val articles: List<TopNewsArticles>? = null
)
