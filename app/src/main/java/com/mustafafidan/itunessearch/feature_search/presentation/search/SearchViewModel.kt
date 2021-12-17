package com.mustafafidan.itunessearch.feature_search.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mustafafidan.itunessearch.constants.MIN_SEARCH_LENGTH
import com.mustafafidan.itunessearch.feature_search.domain.use_case.FetchResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    fetchResultsUseCase: FetchResultsUseCase,
    searchState : SearchState
) : ViewModel() {

    private val _refreshAdapterState = MutableSharedFlow<Unit>(replay = 0)
    val refreshAdapterState = _refreshAdapterState.asSharedFlow()

    private val _state = MutableStateFlow(searchState)
    val state = _state.asStateFlow()

    val flow = fetchResultsUseCase().cachedIn(viewModelScope)

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
                this@SearchViewModel.updateSearchTerm(it)
            }
        }
    }

    private fun updateSearchTerm(searchTerm : String) {
        if(searchTerm.length < MIN_SEARCH_LENGTH) {
            //TODO clear adapter
            return
        }
        this.triggerRefreshAdapter()
    }

    private fun triggerRefreshAdapter() {
        viewModelScope.launch {
            _refreshAdapterState.emit(Unit)
        }
    }

}