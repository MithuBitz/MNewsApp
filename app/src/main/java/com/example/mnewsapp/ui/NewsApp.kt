package com.example.mnewsapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mnewsapp.BottomMenuScreen
import com.example.mnewsapp.MockData
import com.example.mnewsapp.component.BottomMenu
import com.example.mnewsapp.models.NewsManager
import com.example.mnewsapp.models.TopNewsArticles
import com.example.mnewsapp.ui.screen.Categories
import com.example.mnewsapp.ui.screen.DetailScreen
import com.example.mnewsapp.ui.screen.Sources
import com.example.mnewsapp.ui.screen.TopNews

@Composable
fun NewsApp(){
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(navController = navController, scrollState)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(bottomBar = {
        BottomMenu(navController)
    }) {
        Navigation(navController = navController, scrollState = scrollState, paddingValues = it)
    }
}

@Composable
fun Navigation(navController: NavHostController,
               scrollState: ScrollState,
               newsManager: NewsManager = NewsManager(),
               paddingValues: PaddingValues
    ) {
    val articles = mutableListOf(TopNewsArticles())
    articles.addAll(newsManager.newsResponse.value.articles ?: listOf(TopNewsArticles()))

    Log.d("news", "$articles")

    //To check that articles is not empty
    articles?.let {
        NavHost(navController = navController,
            startDestination = BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            //Give the functionality of click on bottomNavigation Items
            //bottomNavigation(navController = navController, articles)
            bottomNavigation(navController = navController, articles, newsManager)

            //create the Detail screen with help of the id argument
            composable("Detail/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    if (newsManager.query.value.isNotEmpty()){
                        articles.clear()
                        articles.addAll(newsManager.searchedNewRespose.value.articles ?: listOf())
                    } else {
                        articles.clear()
                        articles.addAll(newsManager.newsResponse.value.articles ?: listOf())
                    }
                    val article = articles[index]
                    DetailScreen(article, scrollState, navController)
                }
            }
        }
    }

}

fun NavGraphBuilder.bottomNavigation(navController: NavController, articles: List<TopNewsArticles>, newsManager: NewsManager) {
    //This can directed towards another composable while click on bottom bar items
    composable(BottomMenuScreen.TopNews.route){
        TopNews(navController = navController, articles, newsManager.query, newsManager = newsManager)
    }
    composable(BottomMenuScreen.Sources.route){
        Sources(newsManager = newsManager)
    }
    composable(BottomMenuScreen.Categories.route){
        //When nothing is selected than this default value
        newsManager.getArticleByCategory("business")
        newsManager.onSelectedCategoryChanged("business")

        Categories(newsManager = newsManager,
            onFetchCategory = {
                newsManager.onSelectedCategoryChanged(it)
                newsManager.getArticleByCategory(it)
            })
    }

}