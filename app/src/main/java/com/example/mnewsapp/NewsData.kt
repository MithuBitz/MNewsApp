package com.example.mnewsapp

data class NewsData(
    val id: Int,
    val image: Int = R.drawable.royal_enfild,
    val author: String,
    val title: String,
    val description: String,
    val publishedAt: String
)
