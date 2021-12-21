package com.mustafafidan.itunessearch.feature_search.domain.model.remote


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultEntity(
    @Json(name = "trackId")
    val trackId: Int?,
    @Json(name = "collectionId")
    val collectionId: Int?,
    @Json(name = "artworkUrl100")
    val artworkUrl100: String,
    @Json(name = "collectionPrice")
    val collectionPrice: Double?,
    @Json(name = "currency")
    val currency: String?,
    @Json(name = "trackName")
    val trackName: String?,
    @Json(name = "releaseDate")
    val releaseDate: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "artistName")
    val artistName: String?,
    @Json(name = "isStreamable")
    val isStreamable: Boolean? = false,
    @Json(name = "artworkUrl600")
    val artworkUrl600: String?,
    @Json(name = "artworkUrl512")
    val artworkUrl512: String?
)