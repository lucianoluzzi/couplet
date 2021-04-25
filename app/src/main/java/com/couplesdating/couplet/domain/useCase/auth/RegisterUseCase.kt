package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.domain.model.Response

interface RegisterUseCase {
    suspend fun register(
        email: String,
        password: String,
        name: String,
        gender: String
    ): Response
}