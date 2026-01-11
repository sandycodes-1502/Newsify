package com.sandycodes.newsify.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sandycodes.newsify.data.models.Article

class headlinesAdapter(val headlines : List<Article>) : RecyclerView.Adapter<art>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): artic {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: artic, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class ViewHOlder(val  layoutArticleBinding: layout_headline) : RecyclerView.ViewHolder(layoutArticleBinding.root)

}