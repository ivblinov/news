package com.example.news.ui.news_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.news.data.room.Article
import com.example.news.databinding.FragmentNewsScreenBinding
import com.example.news.ui.main_screen.MainScreenViewModel
import com.example.news.ui.main_screen.StateIconSave
import kotlinx.coroutines.launch

private const val ARG_PARAM1 = "param1"
private const val TAG = "MyLog"

class NewsScreenFragment : Fragment() {

    private var _binding: FragmentNewsScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainScreenViewModel by viewModels()

    private var param1: ArticleParcel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var savedList: List<Article>? = null

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.allArticles.collect {articles ->
                    savedList = articles
                    Log.d(TAG, "savedList.lastIndex = ${savedList?.size}")
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.savedStatus.collect { savedStatus ->
                    when (savedStatus) {
                        StateIconSave.Saved -> {
                            param1?.let { viewModel.saveNews(it, savedList?.size) }
                            Log.d(TAG, "_________StateIconSave - Saved")
                        }
                        StateIconSave.NotSaved -> {
                            Log.d(TAG, "StateIconSave - Not Saved")
                        }
                    }
                }
            }
        }

        viewModel.openedNewsScreen()

        with(binding) {
            title.text = param1?.title
            publishedAt.text = param1?.publishedAt
            nameWallpaper.text = param1?.name
            content.text = param1?.content
        }

        Glide
            .with(this)
            .load(param1?.urlToImage)
            .into(binding.poster)
    }

    fun hideStatusBar(window: Window) {
    }

    override fun onStop() {
        super.onStop()
        viewModel.closedNewsScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(param1: ArticleParcel?) = NewsScreenFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PARAM1, param1)
            }
        }
    }
}