package com.example.mnewsapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle

import androidx.compose.ui.unit.dp
import com.example.mnewsapp.R
import com.example.mnewsapp.models.NewsManager
import com.example.mnewsapp.models.TopNewsArticles

@Composable
fun Sources(newsManager: NewsManager){

    //Crete a list for the top bar menu icon
    val items = listOf(
        "TechCrunch" to "techcrunch",
        "TalkSport" to "talksport",
        "Business Insider" to "business-insider",
        "Reuters" to "reuters",
        "Politico" to "politico",
        "TheVerge" to "the-verge"
    )

    Scaffold(topBar = {
        //Design the top app bar with the dropdown menu
        TopAppBar(title = { Text(text = "${newsManager.sourceName.value} Sources")},
                actions = {
                    var menuExpanded by remember {
                        mutableStateOf(false)
                    }
                    IconButton(onClick = {menuExpanded = true}) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }

                    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
                        DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                            items.forEach { 
                                DropdownMenuItem(onClick = {
                                    newsManager.sourceName.value = it.second
                                    menuExpanded = false
                                }) {
                                    Text(it.first)
                                }
                            }
                        }
                    }
                }
            )
    }) {
        //Cal the getArticleBySource function so that list is available
        newsManager.getArticleBySource()
        //Store the list value in a variable so we can iterate through it
        val articles = newsManager.getArticleBySource.value
        SourceContent(articles = articles.articles ?: listOf())
    }
}

@Composable
fun SourceContent(articles: List<TopNewsArticles>){

    //It will help to handle links or url
    val uriHandler = LocalUriHandler.current

    LazyColumn{
        items(articles){
            article ->
            val anotatedString = buildAnnotatedString {
                pushStringAnnotation(
                    tag = "URL",
                    annotation = article.url ?: "newsapi.org"
                )
                withStyle(style = SpanStyle(color = colorResource(id = R.color.purple_500),
                textDecoration = TextDecoration.Underline)
                ){
                    append("Read Full Article Here")
                }
            }
            Card(backgroundColor = colorResource(id = R.color.purple_700), 
                elevation = 8.dp, 
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(200.dp)
                        .padding(end = 8.dp, start = 8.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = article.title ?: "Not Available",
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                        )
                    
                    Text(text = article.description ?: "Not Available",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                        )
                    
                    Card(backgroundColor = Color.White, elevation = 6.dp) {
                        ClickableText(text = anotatedString, modifier = Modifier.padding(8.dp),onClick = {
                            anotatedString.getStringAnnotations(it, it).firstOrNull()?.let {
                                result->
                                if (result.tag == "URL"){
                                    //Open the url in the browser
                                    uriHandler.openUri(result.item)
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}