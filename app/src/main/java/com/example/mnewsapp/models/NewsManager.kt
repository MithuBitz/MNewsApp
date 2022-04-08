package com.example.mnewsapp.models

import android.util.Log
import androidx.compose.runtime.*
import com.example.mnewsapp.networks.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {

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
            _getArticleByCategory
        }

    private val _getArticleByCategory = mutableStateOf(TopNewsResponse())
    val getArticleByCategory: MutableState<TopNewsResponse>
        @Composable get() = remember {
            _getArticleByCategory
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

    init {
        getArticles()
    }

    private fun getArticles(){
        val service = Api.retrofitService.getTopArticles("in")
        //enqueue send request to the server asynchronously and get the callbacks whether it is successful or any failer response
        service.enqueue(object : Callback<TopNewsResponse>{
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful){
                    _newsRespose.value = response.body()!!
                    Log.d("News: ", "${_newsRespose.value}")
                } else {
                    Log.d("error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }

        })
    }

    fun getArticleByCategory(category: String) {
        val service = Api.retrofitService.getArticlesByCategory(category)
        service.enqueue(object : Callback<TopNewsResponse>{
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful){
                    _getArticleByCategory.value = response.body()!!
                    Log.d("category", "${_getArticleByCategory.value}")
                } else {
                    Log.d("error", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }

        })
    }

    fun getArticleBySource() {
        val service = Api.retrofitService.getArticlesBySources(sourceName.value)
        service.enqueue(object : Callback<TopNewsResponse>{
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful){
                    _getArticleBySource.value = response.body()!!
                    Log.d("source", "${_getArticleBySource.value}")
                } else {
                    Log.d("error", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }

        })
    }

    fun getSearchedArticle(query: String){
        val service = Api.retrofitService.getArticleBySearch(query)
        service.enqueue(object : Callback<TopNewsResponse>{
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if (response.isSuccessful){
                    _searchedNewRespose.value = response.body()!!
                    Log.d("search", "${_searchedNewRespose.value}")
                } else {
                    Log.d("search", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("errorSearch", "${t.printStackTrace()}")
            }

        })
    }

    // This function helps to detect which category tab is selected
    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}