package com.mustafafidan.itunessearch.feature_search.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.mustafafidan.itunessearch.feature_search.domain.model.remote.ResultEntity
import org.junit.Before
import org.junit.Test

class FetchResultsMapperTest{

    private lateinit var fetchResultsMapper : FetchResultsMapper

    @Before
    fun setup(){
        fetchResultsMapper = FetchResultsMapper(DateFormatter(), CurrencyConverter())
    }

    @Test
    fun `mapper should map ResultEntity to ResultUi`() {
        val entity = ResultEntity(1,"",0.0,"","","","","",false,"","")

        val resultUiModel = fetchResultsMapper.mapFromResponse(entity)

        assertThat(resultUiModel.id).isEqualTo(1)
    }
}