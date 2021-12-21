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
import com.mustafafidan.itunessearch.common.showSnackBar
import com.mustafafidan.itunessearch.databinding.FragmentSearchBinding
import com.mustafafidan.itunessearch.feature_search.presentation.search.adapter.ResultsAdapter
import com.mustafafidan.itunessearch.feature_search.presentation.search.adapter.ResultsLoadingAdapter
import com.mustafafidan.itunessearch.feature_search.presentation.search.navigation.SearchNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var binding : FragmentSearchBinding

    private var resultsAdapter =  ResultsAdapter(SearchNavigator(this@SearchFragment))
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
        this.setClearBtnClick()

        this.updateSwipeRefresh()
        this.updateNoItemLayoutVisibilityStatus()
        this.updateErrorSnack()
        this.updateAdapter()

        this.observeSearchState()
        this.observeSearchAdapterUpdate()
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

    private fun observeSearchState(){
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.state.collectLatest {
                binding.state = it
            }
        }
    }

    private fun observeSearchAdapterUpdate(){
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.refreshAdapterState
                .collectLatest {
                    this@SearchFragment.onRefresh()
                }
        }
    }

    private fun setRecyclerAdapter(){
        binding.recyclerView.apply {
            resultsAdapter = ResultsAdapter(SearchNavigator(this@SearchFragment))
            val loadingAdapter = ResultsLoadingAdapter(resultsAdapter)
            adapter = resultsAdapter.withLoadStateFooter(footer = loadingAdapter)
        }
    }

    private fun updateAdapter(){
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.flow.collectLatest { pagingData ->
                resultsAdapter.submitData(viewLifecycleOwner.lifecycle,pagingData)
            }
        }
    }

    private fun updateErrorSnack(){
        viewLifecycleOwner.lifecycleScope.launch {
            resultsAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.Error }
                .collectLatest {
                    (it.refresh as? LoadState.Error)?.error?.message?.let { message->
                        if(message.isNotEmpty()){
                            this@SearchFragment.showSnackBar(message)
                        }
                    }
                }
        }
    }

    private fun updateSwipeRefresh(){
        viewLifecycleOwner.lifecycleScope.launch {
            resultsAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .map { it.refresh is LoadState.Loading }
                .collectLatest {
                    binding.swipeRefreshLayout.isRefreshing = it
                }
        }
    }

    private fun updateNoItemLayoutVisibilityStatus(){
        viewLifecycleOwner.lifecycleScope.launch {
            resultsAdapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .map { resultsAdapter.itemCount == 0 }
                .collectLatest {
                    binding.noItemLayout.root.isVisible = it
                }
        }
    }

    override fun onRefresh() {
        resultsAdapter.refresh()
    }
}