package com.mustafafidan.itunessearch.feature_search.data.data_source.remote

import com.mustafafidan.itunessearch.feature_search.domain.model.remote.ResultEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search")
    suspend fun search(
        @Query("term") term : String,
        @Query("limit") limit : Int = 20
    ) : Response<ResultEntity>

}