package com.mustafafidan.itunessearch.feature_search.presentation.search.navigation

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.mustafafidan.itunessearch.R
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultUiModel
import com.mustafafidan.itunessearch.feature_search.presentation.search.SearchFragment

class SearchNavigator(private val searchFragment: SearchFragment) {

    fun navigateToDetail(resultUiModel: ResultUiModel) {
        searchFragment.findNavController().navigate(R.id.action_homeFragment_to_detailFragment,
            bundleOf("resultUiModel" to resultUiModel))
    }
}
