package com.example.news.ui.sources_screen.only_one_source

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.data.retrofit_one_source.OneSource
import com.example.news.databinding.RecyclerItemBinding
import com.example.news.domain.App.Companion.instance
import com.example.news.domain.Screens.newsScreenFragment
import com.example.news.ui.news_screen.ArticleParcel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "MyLog"
class OneSourceRcAdapter(var articles: MutableList<OneSource.Article>) : RecyclerView.Adapter<OneSourceRcAdapter.OneSourceViewHolder>() {

    class OneSourceViewHolder(val binding: RecyclerItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OneSourceViewHolder {
        val binding = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context))
        val holder = OneSourceViewHolder(binding)

        binding.root.setOnClickListener {
            val position: Int = holder.absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                itemClicked(position)
            }
        }
        return holder
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: OneSourceViewHolder, position: Int) {
        val item = articles[position]

        with(holder.binding) {
            articleTitle.text = item.source.name
            article.text = item.title

            Glide
                .with(root)
                .load(item.urlToImage)
                .into(imageArticle)

            Glide
                .with(root)
                .load("https://besticon-demo.herokuapp.com/icon?url=${item.url}&size=50..100..500")
                .into(logo)
        }
    }

    private fun itemClicked(position: Int) {
        val item = articles[position]
        val parseObject = ArticleParcel(
            item.urlToImage,
            item.title,
            item.publishedAt,
            item.content,
            item.source.name,
            item.url
        )
        OnlyOneSourceViewModel.unChangeOneSourceState()
        instance.router.navigateTo(
            newsScreenFragment(
                parseObject
            )
        )
    }

    fun reloadListAdapter(data: MutableList<OneSource.Article>) {
        articles = data
    }
}