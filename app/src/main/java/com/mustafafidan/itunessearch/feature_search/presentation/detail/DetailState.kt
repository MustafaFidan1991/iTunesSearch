package com.mustafafidan.itunessearch.feature_search.presentation.detail

import android.view.View
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultUiModel

class DetailState(
    private val resultUiModel: ResultUiModel?
){
    fun getArtistImageUrl() = resultUiModel?.artistImageUrl

    fun getArtistImageUrlVisibility() = if(resultUiModel?.artistImageUrl == null) View.GONE else View.VISIBLE

    fun getArtistName() = resultUiModel?.artistName

    fun getReleaseDate() = resultUiModel?.date

    fun getName() = resultUiModel?.name

    fun getImageUrl() = resultUiModel?.imageUrl

    fun getDescr() = resultUiModel?.description
}