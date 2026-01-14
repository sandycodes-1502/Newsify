package com.sandycodes.newsify.fragments

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sandycodes.newsify.data.DAO.FavouriteArticleDAO
import com.sandycodes.newsify.data.models.Article
import com.sandycodes.newsify.data.models.favouriteArticle
import com.sandycodes.newsify.database.roomDatabase
import com.sandycodes.newsify.databinding.FragmentNewsDetailsBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "NewsDetailsFragment"
class NewsDetailsFragment() : Fragment() {
    private lateinit var binding: FragmentNewsDetailsBinding
    private lateinit var db : roomDatabase
    private lateinit var favDao : FavouriteArticleDAO
    private var isFav = false

    companion object {
        private const val ARG_ARTICLE = "article"

        fun newInstance(article: Article): NewsDetailsFragment {
            val fragment = NewsDetailsFragment()
            fragment.arguments = Bundle().apply {
                putParcelable(ARG_ARTICLE, article)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView: Binding Created")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = requireArguments().getParcelable<Article>(ARG_ARTICLE)
        Log.i(TAG, "Article: $article")

        binding.newsArticle = article
        db = roomDatabase.getDatabase(requireContext())
        favDao = db.favouriteArticleDAO()
        Log.i(TAG, "onViewCreated: View Created")


         fun updateStarIcon() {
            if (isFav) {
                binding.favouritebtn.setImageResource(R.drawable.star_on)
                Log.i(TAG, "Star Icon Updated")
            } else {
                binding.favouritebtn.setImageResource(R.drawable.star_off)
                Log.i(TAG, "Star Icon Updated")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            isFav = favDao.isFavourite(article?.url?: "")
            withContext(Dispatchers.Main) {
                updateStarIcon()
                Log.i(TAG, "Star Icon Updated")
            }
        }

        binding.favouritebtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val favouriteArticle = favouriteArticle(
                    url = article?.url!!,
                    title = article.title,
                    description = article.description,
                    imageUrl = article.urlToImage,
                    publishedAt = article.publishedAt,
                    content = article.content
                )
                Log.i(TAG, "Favourite Article: $favouriteArticle")

                if (isFav) {
                    favDao.delete(favouriteArticle)
                    isFav = false
                } else {
                    favDao.insert(favouriteArticle)
                    isFav = true
                }

                withContext(Dispatchers.Main) {
                    updateStarIcon()
                    Toast.makeText(requireContext(),
                        if (isFav) "Added to favourites" else "Removed from favourites",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

        Picasso.get().load(article?.urlToImage).into(binding.newsImage)
        Log.i(TAG, "Image set: ${binding.newsImage}")

        binding.redirectURL.setOnClickListener {
            val url = article?.url
            if (url != null) {
                Toast.makeText(requireContext(), "Redirecting to: ${article.source?.name}", Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            else{
                Log.i(TAG, "URL is null")
            }
        }
        Log.i(TAG, "Opened details for: ${article?.title}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: View Destroyed")
    }
}