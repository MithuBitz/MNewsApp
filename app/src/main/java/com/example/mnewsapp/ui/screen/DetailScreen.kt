package com.example.mnewsapp.ui.screen

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mnewsapp.MockData
import com.example.mnewsapp.MockData.getTimeAgo
import com.example.mnewsapp.NewsData
import com.example.mnewsapp.R
import com.example.mnewsapp.models.TopNewsArticles
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun DetailScreen(article: TopNewsArticles, scrollState: ScrollState, navController: NavController) {
    Scaffold(
        topBar = {
            DetailTopAppBar(onBackPressed = {navController.popBackStack()})
        }
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {

            //Load a image from url
            CoilImage(
                imageModel = article.urlToImage,
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(id = R.drawable.iphone),
                placeHolder = ImageBitmap.imageResource(id = R.drawable.iphone)
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                IconWithInfo(icon = Icons.Default.Edit, info = article.author ?: "Not Available")
                IconWithInfo(icon = Icons.Default.DateRange, info = MockData.stringToDate(article.publishedAt!!).getTimeAgo())
            }

            Text(text = article.title ?: "Not Available", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = article.description ?: "Not Available", modifier = Modifier.padding(top = 16.dp))
        }
    }

}

@Composable
fun DetailTopAppBar(onBackPressed: ()-> Unit = {}) {
    TopAppBar(
        title = { Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold)},
        navigationIcon = {
            IconButton(onClick = {onBackPressed()}) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
            }
        }
    )

}

@Composable
fun IconWithInfo(icon: ImageVector, info: String){
    Row {
       Icon(icon, contentDescription = "Author", modifier = Modifier.padding(end = 8.dp),
           colorResource(id = R.color.purple_500)
       )
        Text(info)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview(){
    DetailScreen(
        TopNewsArticles(
        author = "Zee News",
        title = "Ukraine's economy may shrink up to 35% if war continues, says IMF",
        description = "Ukraine`s economy is expected to contract by 10% in 2022 as a result of Russia`s invasion, but the outlook could worsen sharply if the conflict lasts longer, the International Monetary Fund said in a staff report released on Monday.",
        publishedAt = "Mar 14, 2022, 20:06 PM IST"
    ),
    rememberScrollState(),
    rememberNavController()
    )
}