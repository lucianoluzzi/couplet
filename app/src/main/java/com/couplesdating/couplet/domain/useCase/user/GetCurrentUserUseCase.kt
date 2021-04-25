package com.couplesdating.couplet.domain.useCase.user

import com.couplesdating.couplet.domain.model.User

interface GetCurrentUserUseCase {
    suspend fun getCurrentUser(): User?
}