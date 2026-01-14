package com.sandycodes.newsify.data.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sandycodes.newsify.data.models.favouriteArticle

@Dao
interface FavouriteArticleDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: favouriteArticle)

    @Delete
    suspend fun delete(article: favouriteArticle)

    @Query("SELECT * FROM favourites")
    suspend fun getAll(): List<favouriteArticle>

    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE url = :url)")
    suspend fun isFavourite(url: String): Boolean

}