package com.example.news.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.ItemNewsBinding
import com.example.news.models.Article
import com.example.news.views.fragments.home.HomeFragmentDirections

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val List = arrayListOf<Article>()

    inner class NewsViewHolder(val itemBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return List.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val item = List[position]

        holder.itemBinding.article =item


        holder.itemBinding.newsMaterialCardView.setOnClickListener {

            Navigation.findNavController(holder.itemView)
                .navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(item))

        }

    }

    fun updateList(newList: List<Article>) {
        List.clear()
        List.addAll(newList)
        notifyDataSetChanged()
    }
}