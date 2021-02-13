package com.couplesdating.couplet.domain.useCase

interface RegisterUseCase {
    fun register(
        email: String,
        password: String,
        name: String,
        gender: String,
        photo: String? = null
    )
}