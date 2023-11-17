package com.example.news.ui.news_screen

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.news.R
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

        initialScreen()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.allArticles.collect {articles ->
                    savedList = articles
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.savedStatus.collect { savedStatus ->
                    when (savedStatus) {
                        StateIconSave.Saved -> {
                            param1?.let { viewModel.saveNews(it, savedList?.size) }
                        }
                        StateIconSave.NotSaved -> {
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.closedNewsScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initialScreen() {
        viewModel.openedNewsScreen()
        val text = cutText()
        with(binding) {
            title.text = param1?.title
            publishedAt.text = param1?.publishedAt?.toDataConverter() ?: ""
            nameWallpaper.text = param1?.name
            content.text = text
        }
        spannableText(text, param1?.url ?: "")
        Glide
            .with(this)
            .load(param1?.urlToImage)
            .into(binding.poster)
    }

    private fun cutText(): String {
        val text = param1?.content ?: ""
        return if (text.isNotEmpty()) text.substringBeforeLast('[')
        else text
    }

    fun hideStatusBar(window: Window) {
    }

    private fun spannableText(text: String, addressSite: String) {
        var line: Int
        if (addressSite.isBlank()) return
        binding.content.post {
            kotlin.run {
                line = binding.content.lineCount
                val startLine = binding.content.layout.getLineStart(line - 1)
                val endLine = binding.content.layout.getLineEnd(line - 1)
                val spannable: Spannable = SpannableString(text)
                val clickableSpan: ClickableSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        openBrowser(addressSite)
                    }
                }
                spannable.setSpan(clickableSpan, startLine, endLine, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                binding.content.text = spannable
                binding.content.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    fun openBrowser(addressSite: String) {
        val address = Uri.parse(addressSite)
        val openLinkIntent = Intent(Intent.ACTION_VIEW, address)
        if (activity?.let { openLinkIntent.resolveActivity(it.packageManager) } != null) {
            startActivity(openLinkIntent)
        } else { Log.d(TAG, "Не получается обработать намерение!") }
    }

    private fun String.toDataConverter(): String {
        return if (this.count() < 20) ""
        else {
            Log.d(TAG, "s = $this")
            val item = DataConverter()
            item.year = this.substring(0 until  4)
            item.month = this.substring(5 until 7)
            item.day = this.substring(8 until 10)
            item.hour = this.substring(11 until 13)
            item.minute = this.substring(14 until 16)
            item.isMidday = if ("00" <= item.hour && item.hour < "12") "AM" else "PM"

            Log.d(TAG, "Month = ${item.month}")
            item.generateData()
        }
    }

    companion object {
        fun newInstance(param1: ArticleParcel?) = NewsScreenFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PARAM1, param1)
            }
        }
    }
}