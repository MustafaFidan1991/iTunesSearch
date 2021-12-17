package com.mustafafidan.itunessearch.feature_search.presentation.detail

import androidx.lifecycle.ViewModel
import com.mustafafidan.itunessearch.feature_search.domain.use_case.FetchDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchDetailUseCase: FetchDetailUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState(fetchDetailUseCase()))
    val state = _state.asStateFlow()

}