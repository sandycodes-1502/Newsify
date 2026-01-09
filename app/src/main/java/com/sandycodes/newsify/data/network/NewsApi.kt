package com.sandycodes.newsify.data.network

import com.sandycodes.newsify.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.sandycodes.newsify.BuildConfig


interface NewsApi {

    // Home screen
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "in",
        @Query("category") category: String? = null,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): NewsResponse

    // Search screen
    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): NewsResponse

    @GET https://newsapi.org/v2/top-headlines?country=us&apiKey=d68aae0e66474b48a20160a62baldfaced

}
