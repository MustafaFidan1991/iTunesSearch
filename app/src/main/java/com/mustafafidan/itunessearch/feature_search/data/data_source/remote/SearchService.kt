package com.mustafafidan.itunessearch.feature_search.data.data_source.remote

import com.mustafafidan.itunessearch.constants.PAGE_SIZE
import com.mustafafidan.itunessearch.feature_search.domain.model.remote.ResultEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search")
    suspend fun search(
        @Query("term") term : String,
        @Query("offset") offset : Int,
        @Query("media") media : String? = null,
        @Query("limit") limit : Int = PAGE_SIZE
    ) : Response<ResultEntity>

}