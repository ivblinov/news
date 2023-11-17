package com.example.news.ui.sources_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.news.data.retrofite_source.Article
import com.example.news.databinding.FragmentSourcesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


private const val TAG = "MyLog"
class SourcesFragment : Fragment() {

    private var _binding: FragmentSourcesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SourcesViewModel by viewModels()
    lateinit var adapter: SourceRcAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSourcesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.sourceState.collect { sourceState ->
                    when (sourceState) {
                        SourceState.Request -> {
                            val sourceList = viewModel.getSources()
                            createSourceList(sourceList)
                        }
                        SourceState.Received -> {
                            hideProgress()
                        }
                    }
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            CoroutineScope(Dispatchers.IO).launch {
                val sourceList = viewModel.getSources()


                Log.d(TAG, "refresh")
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun hideProgress() {
        binding.progress.isVisible = false
    }

    private fun createSourceList(articles: MutableList<Article>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        with(binding.recyclerViewSource) {
            this.layoutManager = layoutManager

            adapter = SourceRcAdapter(articles)
            binding.recyclerViewSource.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    companion object {
        fun newInstance() = SourcesFragment()
    }
}