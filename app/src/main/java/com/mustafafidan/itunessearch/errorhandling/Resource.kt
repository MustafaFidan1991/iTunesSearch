package com.mustafafidan.itunessearch.errorhandling

sealed class Resource<out T>

class Success<out T>(val successData: T) : Resource<T>()
class Error(val errorMessage: String) : Resource<Nothing>()
class Loading : Resource<Nothing>()