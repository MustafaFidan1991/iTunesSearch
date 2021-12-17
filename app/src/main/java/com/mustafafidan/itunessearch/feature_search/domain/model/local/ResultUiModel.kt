package com.mustafafidan.itunessearch.feature_search.domain.model.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultUiModel(
    val id : Int,
    val imageUrl : String,
    val price : String,
    val name : String,
    val date : String,
    val isStreamable : Boolean,
    val description : String?,
    val artistImageUrl : String?,
    val artistName : String?
) : Parcelable