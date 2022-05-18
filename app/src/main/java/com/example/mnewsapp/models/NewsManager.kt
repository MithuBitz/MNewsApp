package com.example.mnewsapp.models

import android.util.Log
import androidx.compose.runtime.*
import com.example.mnewsapp.networks.Api
import com.example.mnewsapp.networks.NewsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager (private val service: NewsService) {

    //Create and initialze list of Topnewsrespose from the web as json
    //State not only update the data but also change the value in the UI
    private val _newsRespose = mutableStateOf(TopNewsResponse())
    // To make the response available for other classes
    val newsResponse: State<TopNewsResponse>
        @Composable get() = remember {
            _newsRespose
        }

    //Default source name
    val sourceName = mutableStateOf("abc-news")
    //Get the article by its source
    private val _getArticleBySource = mutableStateOf(TopNewsResponse())
    val getArticleBySource: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleBySource
        }



    //To deal with the search bar input text
    val query = mutableStateOf("")
    private val _searchedNewRespose = mutableStateOf(TopNewsResponse())
    val searchedNewRespose: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _searchedNewRespose
        }

    //To check which category tab is selected
    //Mutable state is useful becoz it will reflect or update immediately in every place where it is used
    val selectedCategory: MutableState<ArticleCategory?> = mutableStateOf(null)

    suspend fun getArticles(country: String):TopNewsResponse = withContext(Dispatchers.IO) {

        service.getTopArticles(country = country)
    }
        //enqueue send request to the server asynchronously and get the callbacks whether it is successful or any failer response
//        service.enqueue(object : Callback<TopNewsResponse>{
//            override fun onResponse(
//                call: Call<TopNewsResponse>,
//                response: Response<TopNewsResponse>
//            ) {
//                if (response.isSuccessful){
//                    _newsRespose.value = response.body()!!
//                    Log.d("News: ", "${_newsRespose.value}")
//                } else {
//                    Log.d("error", "${response.errorBody()}")
//                }
//            }
//
//            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
//                Log.d("error", "${t.printStackTrace()}")
//            }
//
//        })
        //Useing MVVM architecture



    suspend fun getArticleByCategory(category: String): TopNewsResponse = withContext(Dispatchers.IO) {
            service.getArticlesByCategory(category = category)
    }

    suspend fun getArticleBySource(source: String): TopNewsResponse = withContext(Dispatchers.IO) {
        service.getArticlesBySources(source)
    }

    suspend fun getSearchedArticle(query: String): TopNewsResponse = withContext(Dispatchers.IO){
        service.getArticleBySearch(query)
    }

    // This function helps to detect which category tab is selected
    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}