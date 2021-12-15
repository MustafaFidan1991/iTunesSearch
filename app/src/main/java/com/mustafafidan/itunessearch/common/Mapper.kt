package com.mustafafidan.itunessearch.common

interface Mapper<T,R> {
    fun mapFromResponse(type: T?) : R
}
