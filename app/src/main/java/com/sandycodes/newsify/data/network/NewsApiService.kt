package com.sandycodes.newsify.data.network

import com.sandycodes.newsify.data.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = "d68aae0e66474b48a20160a62dfbaecb"
    ): NewsResponse

    // Search screen
//    @GET("everything")
//    suspend fun searchNews(
//        @Query("q") query: String,
//        @Query("sortBy") sortBy: String = "publishedAt",
//        @Query("apiKey") apiKey: String = "d68aae0e66474b48a20160a62dfbaecb"
//    ): NewsResponse


}
