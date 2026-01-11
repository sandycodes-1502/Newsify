package com.sandycodes.newsify.data.models

data class NewsResponse(
    val status : String,
    val TotalResults : Int,
    val articles : ArrayList<Article>
)