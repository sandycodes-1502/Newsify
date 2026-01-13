package com.sandycodes.newsify.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sandycodes.newsify.R
import com.sandycodes.newsify.adapters.articleAdapter
import com.sandycodes.newsify.data.models.Article
import com.sandycodes.newsify.data.network.NewsApiService
import com.sandycodes.newsify.databinding.FragmentDashboardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding
    private lateinit var newsView: RecyclerView
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsView = binding.newsView

        //Retrofit init
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsApi = retrofit.create(NewsApiService::class.java)

        val adapter = articleAdapter(mutableListOf()) {article ->
            Log.i("CRASH_TEST", "Lambda received: ${article.title}")
            openNewsDetailsFragment(article) }

        newsView.layoutManager = LinearLayoutManager(requireContext())
        newsView.adapter = adapter

        fun fetchSearchResults(query: String) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = newsApi.searchNews(query)
                val results = response.articles

                withContext(Dispatchers.Main) {
//                    newsView.adapter = articleAdapter(results) { article ->
//                        NewsDetailsFragment.newInstance(results)
//                }
                    adapter.updateData(results)
                }
            }
        }

        fun fetchTopHeadlines() {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = newsApi.getTopHeadlines()
                    val results = response.articles

                    withContext(Dispatchers.Main) {
                        adapter.updateData(results)
                        Log.i("CRASH_TEST", "Articles Fetched: ${results.size}")
                    }
                } catch (e: Exception) {
                    Log.e("CRASH_CHECK", "API Error: ${e.message}")
                }
            }
        }

        fetchTopHeadlines()


//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = newsApi.getTopHeadlines()
//                val headlines = response.articles
//
//                withContext(Dispatchers.Main) {
//                    newsView.layoutManager = LinearLayoutManager(requireContext())
//                    newsView.adapter = articleAdapter(ArrayList(headlines)) { article ->
//                        val fragment = NewsDetailsFragment.newInstance(article)
//
//                        parentFragmentManager.beginTransaction()
//                            .replace(R.id.fragmentContainer, fragment)
//                            .addToBackStack(null)
//                            .commit()
//                    }
//                }
//            } catch (e: Exception) {
//                Log.e("CRASH_CHECK", "API Error: ${e.message}")
//                e.printStackTrace()
//            }
//        }

        binding.newsSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    fetchSearchResults(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()

                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(500)
                }

                if (newText.isNullOrEmpty()) {
                    fetchTopHeadlines()
                } else {
                    fetchSearchResults(newText)
                }
                return true
            }
        })

    }
    private fun openNewsDetailsFragment(article: Article) {
        val fragment = NewsDetailsFragment.newInstance(article)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

}