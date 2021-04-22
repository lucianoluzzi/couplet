package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.domain.model.User

interface GetCurrentUserUseCase {
    suspend fun getCurrentUser(): User?
}