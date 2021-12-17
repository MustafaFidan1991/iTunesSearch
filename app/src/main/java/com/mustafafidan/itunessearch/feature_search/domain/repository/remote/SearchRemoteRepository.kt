package com.mustafafidan.itunessearch.feature_search.domain.repository.remote

import com.mustafafidan.itunessearch.errorhandling.Resource
import com.mustafafidan.itunessearch.feature_search.domain.model.remote.ResultEntity

interface SearchRemoteRepository {

    suspend fun getResults(term : String,media : String,offset : Int) : Resource<ResultEntity>

    fun getResultDetail()

}