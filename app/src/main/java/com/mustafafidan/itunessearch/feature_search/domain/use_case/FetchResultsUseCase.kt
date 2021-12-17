package com.mustafafidan.itunessearch.feature_search.domain.use_case

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.mustafafidan.itunessearch.common.Mapper
import com.mustafafidan.itunessearch.common.getFormattedDate
import com.mustafafidan.itunessearch.constants.PAGE_SIZE
import com.mustafafidan.itunessearch.feature_search.data.paging_source.ResultsPagingSource
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultUiModel
import com.mustafafidan.itunessearch.feature_search.domain.model.remote.ResultEntity
import com.mustafafidan.itunessearch.feature_search.domain.repository.SearchRepository
import com.mustafafidan.itunessearch.feature_search.presentation.search.SearchState
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchResultsUseCase@Inject constructor(
    private val searchRepository: SearchRepository,
    private val mapper : FetchResultsMapper,
    private val searchState: SearchState
) {
    operator fun invoke() =
        Pager(PagingConfig(pageSize = PAGE_SIZE)) {
            ResultsPagingSource(searchRepository,searchState)
        }.flow.map { pagingData ->
            pagingData.map {
                mapper.mapFromResponse(it)
            }
        }
}

class FetchResultsMapper @Inject constructor(
    private val dateFormatter: DateFormatter,
    private val currencyConverter: CurrencyConverter
) : Mapper<ResultEntity, ResultUiModel> {
    override fun mapFromResponse(type: ResultEntity): ResultUiModel {
        return ResultUiModel(
            id = type.trackId,
            imageUrl = type.artworkUrl100,
            price = if(type.collectionPrice == null){
                ""
            }else {
                "${type.collectionPrice} ${currencyConverter.getCurrencyIcon(type.currency)}"
            },
            name = type.collectionName ?: "",
            date = dateFormatter.provideDate(type.releaseDate),
            isStreamable = type.isStreamable ?: false,
            description = type.description,
            artistImageUrl = type.artistViewUrl,
            artistName = type.artistName
        )
    }

}

class CurrencyConverter @Inject constructor() {
    fun getCurrencyIcon(currency : String?) =
        when(currency){
            "USD" -> "$"
            else -> ""
        }
}

class DateFormatter @Inject constructor(
    private val context: Context
) {
    fun provideDate(modifiedDate : String) : String = modifiedDate.getFormattedDate(context)
}