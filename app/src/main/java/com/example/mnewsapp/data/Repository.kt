package com.example.mnewsapp.data

import com.example.mnewsapp.models.NewsManager

class Repository(val manager: NewsManager) {
    suspend fun getArticles() = manager.getArticles("in")
    suspend fun getArticlesByCategory(category: String) = manager.getArticleByCategory(category)

    suspend fun getArticlesBySource(source: String) = manager.getArticleBySource(source = source)

    suspend fun getSearchedArticles(query: String) = manager.getSearchedArticle(query = query)
}