package com.sandycodes.newsify.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sandycodes.newsify.data.models.Article
import com.sandycodes.newsify.databinding.LayoutHeadlineBinding

class articleAdapter(val headlines : MutableList<Article>,
                     private val onItemCLick : (Article) -> Unit
) : RecyclerView.Adapter<articleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i("CRASH_CHECK", "Items count: ${headlines.size}")

        val binding = LayoutHeadlineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(headlines.get(position))
        holder.itemView.setOnClickListener {
            Log.i("CRASH_CHECK", "Item Clicked for ${headlines.get(position).title}")
            onItemCLick(headlines.get(position))
        }
    }

    fun updateData(newList: List<Article>) {
        headlines.clear()
        headlines.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = headlines.size

    inner class ViewHolder(val  layoutHeadlineBinding: LayoutHeadlineBinding) : RecyclerView.ViewHolder(layoutHeadlineBinding.root){
        fun bind(article: Article){
            layoutHeadlineBinding.article = article
        }
    }

}