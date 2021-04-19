package com.couplesdating.couplet.domain.model

sealed class Response {
    data class Error(val errorMessage: String? = null) : Response()
    data class Success<T>(val data: T? = null) : Response()
    object Completed : Response()
}