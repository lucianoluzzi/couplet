package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.domain.model.Response

interface ResetPasswordUseCase {
    suspend fun resetPassword(email: String): Response
}