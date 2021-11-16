package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.domain.network.Response

interface SignOutUseCase {
    suspend fun signOut(): Response
}