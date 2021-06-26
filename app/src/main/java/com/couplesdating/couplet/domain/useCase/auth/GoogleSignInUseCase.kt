package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.domain.network.Response

interface GoogleSignInUseCase {
    suspend fun signIn(idToken: String, displayName: String): Response
}