package com.mustafafidan.itunessearch.feature_search.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mustafafidan.itunessearch.errorhandling.Success
import com.mustafafidan.itunessearch.errorhandling.Error
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultUiModel
import com.mustafafidan.itunessearch.feature_search.domain.use_case.FetchResultsUseCase
import com.mustafafidan.itunessearch.feature_search.presentation.search.SearchState

class ResultsPagingSource(
    private val fetchResultsUseCase: FetchResultsUseCase,
    private val searchState: SearchState
) : PagingSource<Int, ResultUiModel>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ResultUiModel> {
        if(searchState.searchTerm.value.length <= 2){
            return LoadResult.Page(
                data =  listOf(),
                prevKey = null,
                nextKey = null
            )
        }
        return try {
            val oldKey = params.key ?: 0
            when(val result = fetchResultsUseCase(searchState.searchTerm.value,searchState.filterMediaType.value.type,oldKey)){
                is Success -> {
                    val newKey = (result.successData.results?.size ?: 0) + oldKey
                    val nextKey : Int? = if(oldKey == newKey){
                        null
                    } else{
                        newKey
                    }
                    LoadResult.Page(
                        data = result.successData.results ?: listOf(),
                        prevKey = null,
                        nextKey = nextKey
                    )
                }
                is Error -> LoadResult.Error(Throwable(result.errorMessage))
                else -> LoadResult.Error(Throwable())
            }
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultUiModel>): Int? = null
}