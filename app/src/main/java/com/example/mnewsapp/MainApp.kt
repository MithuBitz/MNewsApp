package com.example.mnewsapp

import android.app.Application
import com.example.mnewsapp.data.Repository
import com.example.mnewsapp.models.NewsManager
import com.example.mnewsapp.networks.Api

class MainApp: Application() {
    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repository by lazy {
        Repository(manager = manager)
    }
}