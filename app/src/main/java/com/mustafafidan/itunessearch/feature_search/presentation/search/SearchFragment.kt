package com.mustafafidan.itunessearch.feature_search.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mustafafidan.itunessearch.databinding.FragmentSearchBinding
import com.mustafafidan.itunessearch.feature_search.presentation.search.adapter.ResultsAdapter
import com.mustafafidan.itunessearch.feature_search.presentation.search.adapter.ResultsLoadingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var binding : FragmentSearchBinding
    private lateinit var resultsAdapter: ResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.setupUi()
        this.setRefreshListener()
        this.setRecyclerAdapter()
        this.updateAdapter()
        this.addStateListener()
    }
    private fun setupUi(){
        binding.recyclerView.setHasFixedSize(true)
        binding.swipeRefreshLayout.isRefreshing = true

    }

    private fun setRecyclerAdapter(){
        binding.recyclerView.apply {
            resultsAdapter = ResultsAdapter()
            val loadingAdapter = ResultsLoadingAdapter(resultsAdapter)
            adapter = resultsAdapter.withLoadStateFooter(footer = loadingAdapter)
        }
    }

    private fun updateAdapter(){
        lifecycleScope.launch {
            searchViewModel.flow.collectLatest { pagingData ->
                resultsAdapter.submitData(pagingData)
            }
        }
    }

    private fun addStateListener(){
        resultsAdapter.addLoadStateListener { loadState->
            if (!(loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading)){
                this.finishRefreshing()
            }
        }
    }

    private fun finishRefreshing(){
        if(binding.swipeRefreshLayout.isRefreshing) {
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setRefreshListener(){
        binding.swipeRefreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        resultsAdapter.refresh()
    }
}