package com.example.news.ui.sources_screen.only_one_source

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
import com.example.news.data.retrofit_one_source.OneSource
import com.example.news.databinding.FragmentOneSourceBinding
import kotlinx.coroutines.launch

private const val ARG_ID_SOURCE = "id_source"
private const val TAG = "MyLog"

class OneSourceFragment : Fragment() {
    private var _binding: FragmentOneSourceBinding? = null
    private val binding get() = _binding!!
    private var idSource: String? = null
    private val viewModel: OnlyOneSourceViewModel by viewModels()
    private var loadData = false
    private var rcAdapter: OneSourceRcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idSource = it.getString(ARG_ID_SOURCE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOneSourceBinding.inflate(inflater, container, false)

        val oneSourceLayoutManager = LinearLayoutManager(requireContext())

        binding.recyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val itemCount = oneSourceLayoutManager.itemCount
                    val lastVisibleItem = oneSourceLayoutManager.findViewByPosition(itemCount - 1)

                    if (lastVisibleItem != null && !loadData) {
                        idSource?.let { viewModel.getExtraArticles(it) }
                        showProgress()
                        loadData = true
                    }
                }
            }
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.oneSourceState.collect { oneSourceState ->
                    when (oneSourceState) {
                        OneSourceState.Request -> {
                            val sourceList = idSource?.let { viewModel.getSources(it) }
                            if (sourceList != null) {
                                createOneSourceList(sourceList, oneSourceLayoutManager)
                            }
                        }
                        OneSourceState.Received -> {
                            hideProgress()
                        }
                        OneSourceState.Reload -> {
                            val result = viewModel.updateList()
                            rcAdapter?.let { adapter ->
                                adapter.reloadListAdapter(viewModel.newArticlesList)
                                result.dispatchUpdatesTo(adapter)
                            }
                            viewModel.oldArticlesList = viewModel.newArticlesList
                            viewModel.changeStateReceived()
                            loadData = false
                        }
                    }
                }
            }
        }
        return binding.root
    }

    private fun hideProgress() {
        binding.progress.isVisible = false
    }

    private fun showProgress() {
        binding.progress.isVisible = true
    }

    private fun createOneSourceList(sourceList: MutableList<OneSource.Article>, layoutManager: LinearLayoutManager) {
        with(binding.recyclerView) {
            this.layoutManager = layoutManager
            rcAdapter = OneSourceRcAdapter(sourceList)
            adapter = rcAdapter
            binding.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(idSource: String) =
            OneSourceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID_SOURCE, idSource)
                }
            }
    }
}