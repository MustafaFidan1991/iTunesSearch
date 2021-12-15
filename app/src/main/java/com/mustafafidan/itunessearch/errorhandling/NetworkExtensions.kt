package com.mustafafidan.itunessearch.errorhandling

import retrofit2.Response

fun <T> Response<T>.remote() : Resource<T> {
    return if(isSuccessful){
        this.body()?.let { body->
            Success(body)
        } ?: run {
            Error("api body error")
        }
    } else {
        Error("server error, error code : ${code()}")
    }
}

fun <T,R> Resource<T>.mapOnData(transform : (T) -> R) : Resource<R> {
    return when(this){
        is Success<T> ->{ Success(transform(this.successData)) }
        is Error -> this
        is Loading -> this
    }
}