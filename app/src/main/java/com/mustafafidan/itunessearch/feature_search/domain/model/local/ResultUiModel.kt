package com.mustafafidan.itunessearch.feature_search.domain.model.local

data class ResultUiModel(
    val id : Int,
    val imageUrl : String,
    val price : String,
    val name : String,
    val date : String,
    val isStreamable : Boolean
)