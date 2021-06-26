package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.domain.network.Response
import com.facebook.AccessToken

interface FacebookSignInUseCase {
    suspend fun signIn(token: AccessToken): Response
}