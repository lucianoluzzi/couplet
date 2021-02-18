package com.couplesdating.couplet.domain.model

sealed class Response {
    data class Error(val errorMessage: String? = null) : Response()
    data class Success<T>(val content: T) : Response()
}