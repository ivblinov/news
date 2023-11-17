package com.example.news.ui.sources_screen.only_one_source

import androidx.recyclerview.widget.DiffUtil
import com.example.news.data.retrofit_one_source.OneSource

class OneSourceDiffUtilCallBack(
    private val oldArticleList: MutableList<OneSource.Article>,
    private val newArticleList: MutableList<OneSource.Article>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldArticleList.size

    override fun getNewListSize(): Int = newArticleList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldArticleList[oldItemPosition].url == newArticleList[newItemPosition].url

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldArticleList[oldItemPosition] == newArticleList[newItemPosition]
}