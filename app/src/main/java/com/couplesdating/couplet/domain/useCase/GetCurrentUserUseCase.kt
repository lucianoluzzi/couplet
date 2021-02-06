package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.domain.model.User

interface GetCurrentUserUseCase {
    fun getCurrentUser(): User?
}