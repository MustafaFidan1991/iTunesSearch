package com.mustafafidan.itunessearch.feature_search.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.mustafafidan.itunessearch.constants.PAGE_SIZE
import com.mustafafidan.itunessearch.feature_search.data.paging_source.ResultsPagingSource
import com.mustafafidan.itunessearch.feature_search.domain.use_case.FetchResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val fetchResultsUseCase: FetchResultsUseCase
) : ViewModel() {
    val flow = Pager(PagingConfig(pageSize = PAGE_SIZE)) { ResultsPagingSource(fetchResultsUseCase) }.flow.cachedIn(viewModelScope)
}