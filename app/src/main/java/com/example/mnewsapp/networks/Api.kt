package com.example.mnewsapp.networks

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Api {
    public const val API_KEY = "1aedaf68ae09431fa1fe70d4ecc9d404"
    private const val BASE_URL = "https://newsapi.org/v2/"

    //Build  Moshi for convert
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val logging = HttpLoggingInterceptor()

    //OkHttpClient is used to create a single instance and which is call by each and every class to send http request
    val httpClient = OkHttpClient.Builder().apply {
        addInterceptor(
            Interceptor{
                chain ->
                val builder = chain.request().newBuilder()
                builder.header("X-Api-key", API_KEY)
                return@Interceptor chain.proceed(builder.build())
            }
        )
        //Get the details about on request responses about the http request in Logcat
        logging.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(logging)
    }.build()

    //Build the base url to extract the json data from the news api
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi)) // add moshi converter factory to convert json to kotlin recognizable form
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    //Create a service from the interface or initialize the retrofit service so to say
    val retrofitService: NewsService by lazy {
        retrofit.create(NewsService::class.java)
    }

}