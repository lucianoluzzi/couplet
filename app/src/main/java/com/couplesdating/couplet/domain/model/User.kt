package com.couplesdating.couplet.domain.model

data class User(
    private val email: String,
    private val name: String? = null,
    private val password: String? = null
)