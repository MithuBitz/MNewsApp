package com.example.mnewsapp

import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object MockData {
    val topNewsList = listOf<NewsData>(
        NewsData(id = 1,
            image = R.drawable.royal_enfild,
            author = "Zee News",
            title = "Royal Enfield Scram 411 to launch tomorrow, here's what you may expect",
            description = "The Royal Enfield Scram 411 will be unveiled in India tomorrow, March 15th. The upcoming Royal Enfield bike will have a lot of similarities to the Himalayan in terms of engine and platform, but it will be a very different bike from the ADV.",
            publishedAt = "2022-03-14T20:06:00"
        ),
        NewsData(id = 2,
            image = R.drawable.iphone,
            author = "Zee News",
            title = "Apple iPhone 12 available at Rs 24,900: Here's how to grab this deal",
            description = "The Apple iPhone 12 is one of the greatest premium cell phones available. Despite being more than a year old, the iPhone 12 is still a wonderful deal and as quick as some of the most recent Android flagship phones. It was released in India in 2020 with a starting price of Rs 79,900, but after the release of the iPhone 13 last year, Apple reduced the price to Rs 65,900. However, as part of an incredible deal, you can get the iPhone 12 for as little as Rs 24,900.",
            publishedAt = "2022-03-14T20:06:00"
        ),
        NewsData(id = 3,
            author = "Zee News",
            image = R.drawable.ukraine_war,
            title = "Ukraine's economy may shrink up to 35% if war continues, says IMF",
            description = "Ukraine`s economy is expected to contract by 10% in 2022 as a result of Russia`s invasion, but the outlook could worsen sharply if the conflict lasts longer, the International Monetary Fund said in a staff report released on Monday.",
            publishedAt = "2022-03-14T20:06:00"
        ),
        NewsData(id = 4,
            author = "Zee News",
            title = "Royal Enfield Scram 411 to launch tomorrow, here's what you may expect",
            description = "The Royal Enfield Scram 411 will be unveiled in India tomorrow, March 15th. The upcoming Royal Enfield bike will have a lot of similarities to the Himalayan in terms of engine and platform, but it will be a very different bike from the ADV.",
            publishedAt = "2022-03-14T20:06:00"
        ),
        NewsData(id = 5,
            author = "Zee News",
            title = "Royal Enfield Scram 411 to launch tomorrow, here's what you may expect",
            description = "The Royal Enfield Scram 411 will be unveiled in India tomorrow, March 15th. The upcoming Royal Enfield bike will have a lot of similarities to the Himalayan in terms of engine and platform, but it will be a very different bike from the ADV.",
            publishedAt = "2022-03-14T20:06:00"
        ),
        NewsData(id = 6,
            author = "Zee News",
            title = "Royal Enfield Scram 411 to launch tomorrow, here's what you may expect",
            description = "The Royal Enfield Scram 411 will be unveiled in India tomorrow, March 15th. The upcoming Royal Enfield bike will have a lot of similarities to the Himalayan in terms of engine and platform, but it will be a very different bike from the ADV.",
            publishedAt = "2022-03-14T20:06:00"
        ),
        NewsData(id = 7,
            author = "Zee News",
            title = "Royal Enfield Scram 411 to launch tomorrow, here's what you may expect",
            description = "The Royal Enfield Scram 411 will be unveiled in India tomorrow, March 15th. The upcoming Royal Enfield bike will have a lot of similarities to the Himalayan in terms of engine and platform, but it will be a very different bike from the ADV.",
            publishedAt = "2022-03-14T20:06:00"
        ),
        NewsData(id = 8,
            author = "Zee News",
            title = "Royal Enfield Scram 411 to launch tomorrow, here's what you may expect",
            description = "The Royal Enfield Scram 411 will be unveiled in India tomorrow, March 15th. The upcoming Royal Enfield bike will have a lot of similarities to the Himalayan in terms of engine and platform, but it will be a very different bike from the ADV.",
            publishedAt = "2022-03-14T20:06:00"
        )
    )

    fun getNews(newsId: Int?): NewsData{
        return topNewsList.first { it.id == newsId }
    }

    //Function to calculate the time ago from the news is created
    fun Date.getTimeAgo(): String {
        val calender = Calendar.getInstance()
        calender.time = this

        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)
        val hour = calender.get(Calendar.HOUR_OF_DAY)
        val minute = calender.get(Calendar.MINUTE)

        val currentCalender = Calendar.getInstance()

        val currentYear = currentCalender.get(Calendar.YEAR)
        val currentMonth = currentCalender.get(Calendar.MONTH)
        val currentDay = currentCalender.get(Calendar.DAY_OF_MONTH)
        val currentHour = currentCalender.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentCalender.get(Calendar.MINUTE)

        return if (year < currentYear) {
            val interval = currentYear - year
            if (interval == 1) "$interval year ago" else "$interval years ago"
        } else if (month < currentMonth) {
            val interval = currentMonth - month
            if (interval == 1) "$interval month ago" else "$interval months ago"
        } else if (day < currentDay) {
            val interval = currentDay - day
            if (interval == 1) "$interval day ago" else "$interval days ago"
        } else if (hour < currentHour) {
            val interval = currentHour - hour
            if (interval == 1) "$interval hour ago" else "$interval hours ago"
        } else if (minute < currentMinute) {
            val interval = currentMinute - minute
            if (interval == 1) "$interval minute ago" else "$interval minutes ago"
        } else {
            "a moment ago"
        }
    }

    fun stringToDate(publishedAt: String): Date {
        val date =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(publishedAt)
            } else {
                java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH).parse(publishedAt)
            }
        Log.d("published","$date")
        return date
    }
}