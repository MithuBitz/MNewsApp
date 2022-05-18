package com.example.mnewsapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
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
import com.example.mnewsapp.networks.Api
import com.example.mnewsapp.ui.screen.Categories
import com.example.mnewsapp.ui.screen.DetailScreen
import com.example.mnewsapp.ui.screen.Sources
import com.example.mnewsapp.ui.screen.TopNews

@Composable
fun NewsApp(mainViewModel: MainViewModel){
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    MainScreen(navController = navController, scrollState, mainViewModel = mainViewModel)
}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState, mainViewModel: MainViewModel) {
    Scaffold(bottomBar = {
        BottomMenu(navController)
    }) {
        Navigation(navController = navController, scrollState = scrollState, paddingValues = it, viewModel = mainViewModel)
    }
}

@Composable
fun Navigation(navController: NavHostController,
               scrollState: ScrollState,
               newsManager: NewsManager = NewsManager(Api.retrofitService),
               paddingValues: PaddingValues,
               viewModel: MainViewModel
    ) {
    val articles = mutableListOf(TopNewsArticles())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())

    Log.d("news", "$articles")

    //To check that articles is not empty
    articles?.let {
        NavHost(navController = navController,
            startDestination = BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {

            val queryState = mutableStateOf(viewModel.query.value)
            //Give the functionality of click on bottomNavigation Items
            //bottomNavigation(navController = navController, articles)
            bottomNavigation(navController = navController, articles, query = queryState, viewModel)

            //create the Detail screen with help of the id argument
            composable("Detail/{index}",
                arguments = listOf(navArgument("index") { type = NavType.IntType })
            ) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    if (queryState.value != ""){
                        articles.clear()
                        articles.addAll(viewModel.searchedNewsResponse.value.articles ?: listOf())
                    } else {
                        articles.clear()
                        articles.addAll(viewModel.newsResponse.value.articles ?: listOf())
                    }
                    val article = articles[index]
                    DetailScreen(article, scrollState, navController)
                }
            }
        }
    }

}

fun NavGraphBuilder.bottomNavigation(navController: NavController, articles: List<TopNewsArticles>,
                                     query: MutableState<String>, viewModel: MainViewModel) {
    //This can directed towards another composable while click on bottom bar items
    composable(BottomMenuScreen.TopNews.route){
        TopNews(navController = navController, articles, query, viewModel = viewModel)
    }
    composable(BottomMenuScreen.Sources.route){
        Sources(viewModel = viewModel)
    }
    composable(BottomMenuScreen.Categories.route){
        //When nothing is selected than this default value
        viewModel.getArticlesByCategory("business")
        viewModel.onSelectedCategoryChange("business")

        Categories(viewModel = viewModel,
            onFetchCategory = {
               viewModel.onSelectedCategoryChange(it)
                viewModel.getArticlesByCategory(it)
            })
    }

}