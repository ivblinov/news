package com.example.news.ui.news_screen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.data.room.Article
import com.example.news.databinding.RecyclerItemBinding
import com.example.news.domain.App.Companion.instance
import com.example.news.domain.Screens.newsScreenFragment

class SavedRcAdapter(private val articleList: List<Article>) : RecyclerView.Adapter<SavedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val view = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context))
        return SavedViewHolder(view)
    }

    override fun getItemCount() = articleList.size

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        val item = articleList[position]

        with(holder.binding) {
            article.text = item.title
            articleTitle.text = item.name
            Glide
                .with(root)
                .load(item.urlToImage)
                .into(imageArticle)

            Glide
                .with(root)
                .load("https://besticon-demo.herokuapp.com/icon?url=${item.url}&size=50..100..500")
                .into(logo)
        }

        holder.binding.root.setOnClickListener {
            val parseObject = ArticleParcel(
                item.urlToImage,
                item.title,
                item.publishedAt,
                item.content,
                item.name,
                item.url
            )
            instance.router.navigateTo(
                newsScreenFragment(
                    parseObject
                )
            )
        }
    }
}

class SavedViewHolder(val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)