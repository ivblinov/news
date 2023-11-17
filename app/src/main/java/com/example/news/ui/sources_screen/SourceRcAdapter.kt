package com.example.news.ui.sources_screen

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news.data.retrofite_source.Article
import com.example.news.databinding.RecyclerItemSourceBinding
import com.example.news.domain.App.Companion.instance
import com.example.news.domain.Screens.oneSourceFragment
import com.example.news.ui.main_screen.MainScreenViewModel
import com.example.news.ui.sources_screen.only_one_source.OnlyOneSourceViewModel

private const val TAG = "MyLog"
class SourceRcAdapter(val articles: MutableList<Article>) : RecyclerView.Adapter<SourceRcAdapter.SourceViewHolder>() {


    private var countryMap = mutableMapOf(
        "us" to "USA"
    )

    class SourceViewHolder(val binding: RecyclerItemSourceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val view = RecyclerItemSourceBinding.inflate(LayoutInflater.from(parent.context))
        return SourceViewHolder(view)
    }

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val item = articles[position]

        with(holder.binding) {

            sourceName.text = item.name
            val text = "${item.category.replaceFirstChar { it.uppercase() }} | ${countryMap[item.country]}"
            sourceCategory.text = text

            Glide
                .with(root)
                .load("https://besticon-demo.herokuapp.com/icon?url=${item.url}&size=50..100..500")
                .into(logo)
        }

        holder.binding.recyclerItem.setOnClickListener {

            MainScreenViewModel.changeTitleAppBar(item.name)
            OnlyOneSourceViewModel.changeButtonVisible()
            SourcesViewModel.changeSourceStatusOnRequest()
            instance.router.navigateTo(oneSourceFragment(item.id))
        }
    }
}

