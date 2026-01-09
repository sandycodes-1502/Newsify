package com.sandycodes.newsify.data.repository

import com.sandycodes.newsify.data.models.Article
import com.sandycodes.newsify.data.network.RetrofitInstance

class NewsRepository {

    suspend fun getHeadlines(category: String? = null): List<Article> {
        return RetrofitInstance.api
            .getTopHeadlines(category = category)
            .articles
    }

    suspend fun search(query: String): List<Article> {
        return RetrofitInstance.api
            .searchNews(query)
            .articles
    }

}
