package com.mustafafidan.itunessearch.feature_search.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mustafafidan.itunessearch.databinding.ItemResultBinding
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultUiModel

class ResultsAdapter : PagingDataAdapter<ResultUiModel, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<ResultUiModel>() {
            override fun areItemsTheSame(oldItem: ResultUiModel, newItem: ResultUiModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ResultUiModel, newItem: ResultUiModel): Boolean =
                oldItem.id == newItem.id
        }
    }

    private fun getItemViewState(position: Int) : ResultState {
        return ResultState(getItem(position)!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CharactersViewHolder)?.bind(item = getItemViewState(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharactersViewHolder(ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    class CharactersViewHolder(private val binding : ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ResultState?) {
            binding.state = item
        }

    }


}