package com.sandycodes.newsify.data.network

import com.sandycodes.newsify.data.models.Article
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    // Home screen
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "in",
        @Query("apiKey") apiKey: String = "d68aae0e66474b48a20160a62dfbaecb"
    ): ArrayList<Article>

    // Search screen
//    @GET("everything")
//    suspend fun searchNews(
//        @Query("q") query: String,
//        @Query("sortBy") sortBy: String = "publishedAt",
//        @Query("apiKey") apiKey: String = "d68aae0e66474b48a20160a62dfbaecb"
//    ): NewsResponse


}
