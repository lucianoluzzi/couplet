package com.couplesdating.couplet.domain.model

data class User(
    val email: String,
    val name: String? = null,
    val password: String? = null
)