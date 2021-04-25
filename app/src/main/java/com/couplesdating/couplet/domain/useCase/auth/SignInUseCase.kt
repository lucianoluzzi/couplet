package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.domain.model.User

interface SignInUseCase {
    suspend fun signIn(email: String, password: String): User?
}