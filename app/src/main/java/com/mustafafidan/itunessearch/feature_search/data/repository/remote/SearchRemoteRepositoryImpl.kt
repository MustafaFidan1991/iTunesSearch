package com.mustafafidan.itunessearch.feature_search.data.repository.remote

import com.mustafafidan.itunessearch.errorhandling.remote
import com.mustafafidan.itunessearch.feature_search.data.data_source.remote.SearchService
import com.mustafafidan.itunessearch.feature_search.domain.repository.remote.SearchRemoteRepository

class SearchRemoteRepositoryImpl(
    private val searchService: SearchService
) : SearchRemoteRepository {

    override suspend fun getResults(term : String,media : String,offset : Int) = searchService.search(term,offset,media).remote()

    override fun getResultDetail() {

    }

}