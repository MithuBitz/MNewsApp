package com.example.mnewsapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mnewsapp.MockData
import com.example.mnewsapp.MockData.getTimeAgo
import com.example.mnewsapp.NewsData
import com.example.mnewsapp.R
import com.example.mnewsapp.component.SearchBar
import com.example.mnewsapp.models.NewsManager
import com.example.mnewsapp.models.TopNewsArticles
import com.example.mnewsapp.models.TopNewsResponse
import com.example.mnewsapp.ui.MainViewModel
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun TopNews(navController: NavController,
            articles: List<TopNewsArticles>,
            query: MutableState<String>,
            viewModel: MainViewModel){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        //Text(text = "Top News", fontWeight = FontWeight.SemiBold)
        //To show the search bar
        SearchBar(query = query, viewModel = viewModel)

        //After Search the serch top news list will be stored so that it will display on the lazy
        val searchText = query.value
        val resultList = mutableListOf<TopNewsArticles>()
        if (searchText != "") {
            resultList.addAll(viewModel.searchedNewsResponse.collectAsState().value.articles ?: articles)
        } else {
            resultList.addAll(articles)
        }

        LazyColumn {
            items(resultList.size){
                index ->
                TopNewsItems(article = resultList[index],
                onNewsClick = {navController.navigate("Detail/$index")})
            }
        }
    }
}

@Composable
fun TopNewsItems(article: TopNewsArticles, onNewsClick: ()-> Unit = {}){
    Box(modifier = Modifier
        .height(200.dp)
        .padding(8.dp)
        .clickable {
            onNewsClick()
        }) {
        
        CoilImage(
            imageModel = article.urlToImage,
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(id = R.drawable.iphone),
            placeHolder = ImageBitmap.imageResource(id = R.drawable.iphone)
        )
        
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = 16.dp, start = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            article.publishedAt?. let {
                Text(text = MockData.stringToDate(article.publishedAt!!).getTimeAgo(),
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White)
            }
            
            Spacer(modifier = Modifier.height(80.dp))

            article.title?. let {
                Text(text = article.title!!, color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopNewsPreview(){
    //TopNews(rememberNavController())
    TopNewsItems(
        TopNewsArticles(

        author = "Zee News",
        title = "Royal Enfield Scram 411 to launch tomorrow, here's what you may expect",
        description = "The Royal Enfield Scram 411 will be unveiled in India tomorrow, March 15th. The upcoming Royal Enfield bike will have a lot of similarities to the Himalayan in terms of engine and platform, but it will be a very different bike from the ADV.",
        publishedAt = "Mar 14, 2022, 20:06 PM IST")
    )
}