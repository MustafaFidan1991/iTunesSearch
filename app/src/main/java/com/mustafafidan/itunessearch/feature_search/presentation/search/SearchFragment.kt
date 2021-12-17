package com.mustafafidan.itunessearch.feature_search.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map


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
        this.setRecyclerAdapter()
        this.updateAdapter()
        this.updateSwipeRefresh()
        this.updateNoItemLayoutVisibilityStatus()
        this.observeSearchState()
        this.observeRefreshAdapter()
        this.setClearBtnClick()
    }

    private fun setClearBtnClick(){
        binding.searchBar.clearBtn.setOnClickListener {
            binding.searchBar.searchEt.setText("")
        }
    }

    private fun setupUi(){
        binding.recyclerView.setHasFixedSize(true)
        binding.swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun observeRefreshAdapter(){
        lifecycleScope.launchWhenStarted {
            searchViewModel.refreshAdapterState.collectLatest {
                this@SearchFragment.onRefresh()
            }
        }
    }

    private fun observeSearchState(){
        lifecycleScope.launchWhenStarted {
            searchViewModel.state.collectLatest {
                binding.state = it
            }
        }
    }

    private fun setRecyclerAdapter(){
        binding.recyclerView.apply {
            resultsAdapter = ResultsAdapter()
            val loadingAdapter = ResultsLoadingAdapter(resultsAdapter)
            adapter = resultsAdapter.withLoadStateFooter(footer = loadingAdapter)
        }
    }

    private fun updateAdapter(){
        lifecycleScope.launchWhenStarted {
            searchViewModel.flow.collectLatest { pagingData ->
                resultsAdapter.submitData(pagingData)
            }
        }
    }

    private fun updateSwipeRefresh(){
        lifecycleScope.launchWhenStarted {
            resultsAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .map { it.refresh is LoadState.Loading }
                .collectLatest {
                    binding.swipeRefreshLayout.isRefreshing = it
                }
        }
    }

    private fun updateNoItemLayoutVisibilityStatus(){
        lifecycleScope.launchWhenStarted {
            resultsAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .map { it.refresh is LoadState.NotLoading && resultsAdapter.itemCount == 0 }
                .collectLatest {
                    binding.noItemLayout.root.isVisible = it
                }
        }
    }

    override fun onRefresh() {
        resultsAdapter.refresh()
    }
}