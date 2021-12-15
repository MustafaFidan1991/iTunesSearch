package com.mustafafidan.itunessearch.feature_search.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mustafafidan.itunessearch.R
import com.mustafafidan.itunessearch.databinding.ItemLoadingStateBinding

class ResultsLoadingAdapter(
    private val adapter : ResultsAdapter
) : LoadStateAdapter<ResultsLoadingAdapter.HomeStateItemViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        HomeStateItemViewHolder(
            ItemLoadingStateBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_loading_state, parent, false)
            )
        ) { adapter.retry() }

    override fun onBindViewHolder(holder: HomeStateItemViewHolder, loadState: LoadState) = holder.bind(loadState)

    class HomeStateItemViewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }
}