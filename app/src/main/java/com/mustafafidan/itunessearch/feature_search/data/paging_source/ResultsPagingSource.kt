package com.mustafafidan.itunessearch.feature_search.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mustafafidan.itunessearch.constants.MIN_SEARCH_LENGTH
import com.mustafafidan.itunessearch.errorhandling.Error
import com.mustafafidan.itunessearch.errorhandling.Success
import com.mustafafidan.itunessearch.feature_search.domain.model.remote.ResultEntity
import com.mustafafidan.itunessearch.feature_search.domain.repository.SearchRepository
import com.mustafafidan.itunessearch.feature_search.presentation.search.SearchState

class ResultsPagingSource(
    private val searchRepository: SearchRepository,
    private val searchState: SearchState
) : PagingSource<Int, ResultEntity>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ResultEntity> {
        if(searchState.searchTerm.value.length < MIN_SEARCH_LENGTH){
            return LoadResult.Page(
                data = listOf(),
                prevKey = null,
                nextKey = null
            )
        }
        return try {
            val oldKey = params.key ?: 0
            when(val result = searchRepository.getResults(searchState.searchTerm.value,searchState.filterMediaType.value.type,oldKey)){
                is Success -> {
                    val newKey = (result.successData.resultCount ?: 0) + oldKey
                    val nextKey : Int? = if(oldKey == newKey) null else newKey
                    LoadResult.Page(
                        data = result.successData.results,
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

    override fun getRefreshKey(state: PagingState<Int, ResultEntity>): Int? = null

}