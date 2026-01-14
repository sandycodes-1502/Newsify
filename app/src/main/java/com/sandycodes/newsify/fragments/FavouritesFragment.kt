
package com.sandycodes.newsify.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandycodes.newsify.R
import com.sandycodes.newsify.adapters.favouritesAdapter
import com.sandycodes.newsify.data.DAO.FavouriteArticleDAO
import com.sandycodes.newsify.data.models.Article
import com.sandycodes.newsify.data.models.favouriteArticle
import com.sandycodes.newsify.database.roomDatabase
import com.sandycodes.newsify.databinding.FragmentFavouritesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "Favourites_Fragment"
class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favDao: FavouriteArticleDAO
    private lateinit var adapter: favouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        Log.i(TAG, "Binding Created")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = favouritesAdapter { article ->
            openNewsDetails(article)
        }

        Log.i(TAG, "Adapter Created")

        favDao = roomDatabase.getDatabase(requireContext()).favouriteArticleDAO()
        Log.i(TAG, "DAO initialized: $favDao")

        binding.favouritesView.layoutManager = LinearLayoutManager(requireContext())
        binding.favouritesView.adapter = adapter
        binding.favouritesView.setHasFixedSize(true)

        loadFavourites()

    }

    override fun onResume() {
        super.onResume()
        loadFavourites()
        Log.i(TAG, "Favourites Resumed")
    }

    private fun loadFavourites() {6
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            Log.i(TAG, "Load Favourites Called")
            try {
                val list = favDao.getAll()
                Log.i(TAG, "DB returned list, size = ${list.size}")

                withContext(Dispatchers.Main) {
                    if (list.isEmpty()) {
                        Log.i(TAG, "No favourites found")
                    }
                    adapter.submitList(list)
                    Log.i(TAG, "Favourites Loaded into adapter")
                }

            } catch (e: Exception) {
                Log.e(TAG, "DB Crash: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun favouriteArticle.toArticle(): Article {
        Log.i(TAG, "Article returned")
        return Article(
            title = title,
            description = description,
            url = url,
            urlToImage = imageUrl,
            publishedAt = publishedAt,
            content = content,
            source = null,
            author = null,
            totalResult = null
        )
    }

    private fun openNewsDetails(article: favouriteArticle) {
        val fragment = NewsDetailsFragment.newInstance(article.toArticle())
        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainer, fragment)
            .add(R.id.fragmentContainer, fragment)
            .hide(this)
            .addToBackStack(null)
            .commit()
        Log.i(TAG, "News Details Fragment Opened")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "Favourites Fragment Destroyed")
    }

}