package com.sandycodes.newsify.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.sandycodes.newsify.R
import com.sandycodes.newsify.data.repository.NewsRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        lifecycleScope.launch {
            try {
                val articles = NewsRepository().getHeadlines()
                Log.d("TEST", "Articles fetched: ${articles.size}")
                Log.d("TEST", "First title: ${articles.firstOrNull()?.title}")
            } catch (e: Exception) {
                Log.e("TEST", "Error: ${e.message}")
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}