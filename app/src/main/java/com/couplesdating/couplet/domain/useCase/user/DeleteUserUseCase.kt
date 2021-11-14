package com.couplesdating.couplet.domain.useCase.user

import com.couplesdating.couplet.domain.network.Response

interface DeleteUserUseCase {
    suspend fun deleteUser(userId: String): Response
}