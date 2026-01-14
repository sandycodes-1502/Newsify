package com.sandycodes.newsify.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sandycodes.newsify.data.models.favouriteArticle
import com.sandycodes.newsify.databinding.LayoutFavouriteBinding

class favouritesAdapter (
    private val onClick: (favouriteArticle) -> Unit
) : RecyclerView.Adapter<favouritesAdapter.ViewHolder>() {

    private val list = mutableListOf<favouriteArticle>()

    fun submitList(newList : List<favouriteArticle>) {
        Log.d("FAV_ADAPTER", "Submit List called with size ${newList.size}")
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = LayoutFavouriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list.get(position))
        Log.d("Favourite", "Binding position $position : ${list[position].title}")
        holder.itemView.setOnClickListener {
            onClick(list.get(position))
        }

    }

    inner class ViewHolder(val layoutfavouritebinding: LayoutFavouriteBinding) : RecyclerView.ViewHolder(layoutfavouritebinding.root) {
        fun bind(article: favouriteArticle) {
            layoutfavouritebinding.title.text = article.title
            layoutfavouritebinding.description.text = article.description
            layoutfavouritebinding.publishtime.text = article.publishedAt
//            layoutfavouritebinding.favouriteArticle = article
        }
    }

}