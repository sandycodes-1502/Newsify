package com.sandycodes.newsify.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sandycodes.newsify.R
import com.sandycodes.newsify.data.network.NewsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashboardFragment : Fragment() {

    private lateinit var headlines_view: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        headlines_view = view.findViewById(R.id.headlines_view)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsApi = retrofit.create(NewsApi::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            val response = newsApi.getTopHeadlines()
            withContext(Dispatchers.Main) {
                headlines_view.adapter = HeadlinesAdapter(response.articles)
            }
        }
        return view
    }

}