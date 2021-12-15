package com.mustafafidan.itunessearch.feature_search.data.repository

import com.mustafafidan.itunessearch.feature_search.domain.repository.SearchRepository
import com.mustafafidan.itunessearch.feature_search.domain.repository.remote.SearchRemoteRepository

class SearchRepositoryImpl(
    private val searchRemoteRepository: SearchRemoteRepository
) : SearchRepository {

    override suspend fun getResults(term : String) = searchRemoteRepository.getResults(term)

    override fun getResultDetail() {

    }

}