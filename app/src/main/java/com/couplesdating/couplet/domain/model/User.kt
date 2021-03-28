package com.couplesdating.couplet.domain.model

data class User(
    val userId: String,
    val email: String?,
    val name: String? = null,
    val password: String? = null,
    val pairedPartner: User? = null
)