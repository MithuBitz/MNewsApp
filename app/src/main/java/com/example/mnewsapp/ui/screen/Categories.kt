package com.example.mnewsapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.mnewsapp.MockData
import com.example.mnewsapp.MockData.getTimeAgo
import com.example.mnewsapp.R
import com.example.mnewsapp.models.NewsManager
import com.example.mnewsapp.models.TopNewsArticles
import com.example.mnewsapp.models.getAllArticleCategory
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun Categories(onFetchCategory: (String) -> Unit = {}, newsManager: NewsManager) {
    val tabItem = getAllArticleCategory()
    Column {
        LazyRow{
            items(tabItem.size){
                val category = tabItem[it]
                CategoryTab(category = category.categoryName,
                    onFetchCategory = onFetchCategory,
                    isSelected = newsManager.selectedCategory.value == category)
            }
        }

        ArticleContent(articles = newsManager.getArticleByCategory.value.articles ?: listOf())
    }
}

@Composable
fun CategoryTab(category: String, isSelected: Boolean = false, onFetchCategory: (String) -> Unit){
    val background = if (isSelected) colorResource(id = R.color.purple_200)
                        else colorResource(id = R.color.purple_700)
    Surface(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 16.dp)
            .clickable { onFetchCategory(category) },
        shape = MaterialTheme.shapes.small,
        color = background
    ) {
        Text(text = category,
                style = MaterialTheme.typography.body2,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
    }

}

@Composable
fun ArticleContent(articles: List<TopNewsArticles>, modifier: Modifier = Modifier){
    LazyColumn{
        items(articles){
            article->
            Card(modifier.padding(8.dp), border = BorderStroke(2.dp, color = colorResource(id = R.color.purple_500))) {
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                    CoilImage(
                        imageModel = article.urlToImage,
                        modifier = Modifier.size(100.dp),
                        placeHolder = painterResource(id = R.drawable.iphone),
                        error = painterResource(id = R.drawable.iphone)                        
                    )
                    
                    Column(modifier.padding(8.dp)) {
                        Text(text = article.title ?: "Not Availabel",
                            fontWeight = FontWeight.Bold,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                        
                        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = article.author ?: "Not Available")
                            Text(text = MockData.stringToDate(article.publishedAt ?: "2022-03-14T20:06:00").getTimeAgo())
                        }

                    }
                }                
            }
        }
    }
}