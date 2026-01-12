package com.sandycodes.newsify.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sandycodes.newsify.R
import com.sandycodes.newsify.adapters.articleAdapter
import com.sandycodes.newsify.data.network.NewsApiService
import com.sandycodes.newsify.databinding.FragmentDashboardBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding
    private lateinit var headlines_view: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        headlines_view = view.findViewById(R.id.headlines_view)

        Log.i("CRASH_CHECK", "Dashboard fragment created")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        Log.i("CRASH_CHECK", "Retrofit initialized")

        val newsApi = retrofit.create(NewsApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.i("CRASH_CHECK", "Calling API now")
                val response = newsApi.getTopHeadlines()
                val headlines = response.articles

                Log.i("CRASH_CHECK", "API call successful")

                withContext(Dispatchers.Main) {
                    headlines_view.layoutManager = LinearLayoutManager(requireContext())
                    headlines_view.adapter = articleAdapter(ArrayList(headlines)) { article ->
                        val fragment = NewsDetailsFragment.newInstance(article)

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer, fragment)
                            .addToBackStack(null)
                            .commit()
                    }

                    Log.i("CRASH_CHECK", "Headlines fetched")
                    Log.i("CRASH_CHECK", "Headlines count: ${headlines.size}")
                }
            } catch (e: Exception) {
                Log.e("CRASH_CHECK", "API Error: ${e.message}")
                e.printStackTrace()
            }
        }

        return view
    }

}