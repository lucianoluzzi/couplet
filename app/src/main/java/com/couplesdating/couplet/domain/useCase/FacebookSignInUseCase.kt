package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.domain.model.Response
import com.facebook.AccessToken

interface FacebookSignInUseCase {
    suspend fun signIn(token: AccessToken): Response
}