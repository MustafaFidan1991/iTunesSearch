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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val fetchResultsUseCase: FetchResultsUseCase,
    searchState : SearchState
) : ViewModel() {

    private val _refreshAdapterState = MutableSharedFlow<Unit>(replay = 0)
    val refreshAdapterState = _refreshAdapterState.asSharedFlow()

    private val _state = MutableStateFlow(searchState)
    val state = _state.asStateFlow()

    val flow = Pager(PagingConfig(pageSize = PAGE_SIZE)) {
        ResultsPagingSource(fetchResultsUseCase,searchState)
    }.flow.cachedIn(viewModelScope)

    init {
        this.listenSearchTerm()
        this.listenFilterMedia()
    }

    private fun listenFilterMedia(){
        viewModelScope.launch {
            _state.value.filterMediaType.collectLatest {
                this@SearchViewModel.triggerRefreshAdapter()
            }
        }
    }

    private fun listenSearchTerm(){
        viewModelScope.launch {
            _state.value.searchTerm.collectLatest {
                this@SearchViewModel.triggerRefreshAdapter()
            }
        }
    }

    private fun triggerRefreshAdapter() {
        viewModelScope.launch {
            _refreshAdapterState.emit(Unit)
        }
    }

}