package com.mustafafidan.itunessearch.feature_search.domain.use_case

import androidx.lifecycle.SavedStateHandle
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultUiModel
import javax.inject.Inject

class FetchDetailUseCase @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) {

    operator fun invoke() = savedStateHandle.get<ResultUiModel>("resultUiModel")

}