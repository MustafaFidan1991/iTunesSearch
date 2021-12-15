package com.mustafafidan.itunessearch.feature_search.domain.use_case

import android.content.Context
import com.mustafafidan.itunessearch.common.Mapper
import com.mustafafidan.itunessearch.common.getFormattedDate
import com.mustafafidan.itunessearch.errorhandling.mapOnData
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultUiModel
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultsUiModel
import com.mustafafidan.itunessearch.feature_search.domain.model.remote.ResultEntity
import com.mustafafidan.itunessearch.feature_search.domain.repository.SearchRepository
import javax.inject.Inject

class FetchResultsUseCase@Inject constructor(
    private val searchRepository: SearchRepository,
    private val mapper : FetchResultsMapper
) {
    suspend operator fun invoke(term : String) = searchRepository.getResults(term).mapOnData { mapper.mapFromResponse(it) }
}

class FetchResultsMapper @Inject constructor(
    private val dateFormatter: DateFormatter
) : Mapper<ResultEntity?, ResultsUiModel> {
    override fun mapFromResponse(type: ResultEntity?): ResultsUiModel {
        return ResultsUiModel(type?.results?.map {
            ResultUiModel(
                id = it.trackId,
                imageUrl = it.artworkUrl100,
                price = "${it.collectionPrice} ${it.currency}",
                name = it.collectionName,
                date = dateFormatter.provideDate(it.releaseDate),
                isStreamable = it.isStreamable ?: false
            )
        })
    }
}


class DateFormatter @Inject constructor(
    private val context: Context
) {
    fun provideDate(modifiedDate : String) : String = modifiedDate.getFormattedDate(context)
}