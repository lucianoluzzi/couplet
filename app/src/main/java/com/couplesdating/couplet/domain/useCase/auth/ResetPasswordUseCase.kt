package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.domain.network.Response

interface ResetPasswordUseCase {
    suspend fun resetPassword(email: String): Response
}