package com.couplesdating.couplet.domain.useCase.user

import com.couplesdating.couplet.domain.network.Response

interface UpdateUserUseCase {
    suspend fun updateNameAndGender(name: String, gender: String): Response
}