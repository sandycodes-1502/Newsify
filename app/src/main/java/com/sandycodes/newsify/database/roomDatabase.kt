package com.sandycodes.newsify.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sandycodes.newsify.data.DAO.FavouriteArticleDAO
import com.sandycodes.newsify.data.models.favouriteArticle

@Database(entities = [favouriteArticle::class], version = 2, exportSchema = false)
abstract class roomDatabase : RoomDatabase() {
    abstract fun favouriteArticleDAO(): FavouriteArticleDAO

    companion object {
        @Volatile private var INSTANCE: roomDatabase? = null

        fun getDatabase(context: Context): roomDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    roomDatabase::class.java,
                    "newsify_db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}