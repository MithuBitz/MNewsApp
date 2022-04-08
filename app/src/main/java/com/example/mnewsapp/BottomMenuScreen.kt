package com.example.mnewsapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomMenuScreen(val route: String,val icon: ImageVector, val title: String){

    //Create an object for each and every item of the bottom bar
    object TopNews: BottomMenuScreen("top news", icon = Icons.Outlined.Home, "Top News")

    object Categories: BottomMenuScreen("categories", icon = Icons.Outlined.Category, "Categories")

    object Sources: BottomMenuScreen("sources", icon = Icons.Outlined.Home, "Sources")
}
