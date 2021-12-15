package com.mustafafidan.itunessearch.feature_search.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mustafafidan.itunessearch.errorhandling.Success
import com.mustafafidan.itunessearch.errorhandling.Error
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultUiModel
import com.mustafafidan.itunessearch.feature_search.domain.use_case.FetchResultsUseCase

class ResultsPagingSource(
    private val fetchResultsUseCase: FetchResultsUseCase
) : PagingSource<Int, ResultUiModel>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ResultUiModel> {
        return try {
            val offset = params.key ?: 0
            when(val result = fetchResultsUseCase("micheal jackson")){
                is Success -> LoadResult.Page(
                    data = result.successData.results ?: listOf(),
                    prevKey = null,
                    nextKey = (result.successData.results?.size ?: 0) + offset
                )
                is Error -> LoadResult.Error(Throwable(result.errorMessage))
                else -> LoadResult.Error(Throwable())
            }
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultUiModel>): Int? = null
}