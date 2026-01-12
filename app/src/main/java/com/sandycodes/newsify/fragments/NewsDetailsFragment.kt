package com.sandycodes.newsify.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sandycodes.newsify.R
import com.sandycodes.newsify.data.models.Article
import com.sandycodes.newsify.databinding.FragmentNewsDetailsBinding

class NewsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailsBinding

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = requireArguments().getParcelable<Article>(ARG_ARTICLE)
        Log.i("CRASH_TEST", "Article: $article")

        binding.title.text = article?.title ?: "No title"
        binding.description.text = article?.description ?: "No description"
        binding.content.text = article?.content ?: "No content preview available"
        binding.PublishedAt.text = article?.publishedAt ?: "No date"
        Log.i("CRASH_TEST", "Title set: ${binding.title.text}")
        Log.i("CRASH_TEST", "Desc set: ${binding.description.text}")
        Log.i("CRASH_TEST", "Content set: ${binding.content.text}")
        Log.i("CRASH_TEST", "Date set: ${binding.PublishedAt.text}")


        binding.redirectURL.setOnClickListener {
            val url = article?.url
            if (url != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            else{
                Log.i("DETAILS_TEST", "URL is null")
            }
        }
        Log.i("DETAILS_TEST", "Opened details for: ${article?.title}")
    }
}