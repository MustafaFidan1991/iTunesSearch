package com.mustafafidan.itunessearch.feature_search.presentation.search.adapter

import android.content.Context
import android.view.View
import com.mustafafidan.itunessearch.R
import com.mustafafidan.itunessearch.feature_search.domain.model.local.ResultUiModel

class ResultState(
    private val result : ResultUiModel
) {

    fun getBtnVisibility() = if(result.price.isEmpty()) View.INVISIBLE else View.VISIBLE

    fun getImageUrl() = result.imageUrl

    fun getName() = result.name

    fun getPrice() = result.price

    fun getDate(context : Context) = context.getString(R.string.release_date,result.date)

    fun getStreamImageVisibility() = if(result.isStreamable) View.VISIBLE else View.GONE

    fun getDetailDto() = result
}