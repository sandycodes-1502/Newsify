package com.sandycodes.newsify.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class favouriteArticle(
    @PrimaryKey val url: String,

    val title: String?,
    val description: String?,
    val imageUrl: String?,
    val publishedAt: String?,
    val content: String?,
)
