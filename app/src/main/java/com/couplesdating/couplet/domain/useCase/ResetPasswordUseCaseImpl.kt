package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.UserRepository
import com.couplesdating.couplet.domain.model.Response

class ResetPasswordUseCaseImpl(
    private val userRepository: UserRepository
) : ResetPasswordUseCase {

    override suspend fun resetPassword(email: String): Response {
        return userRepository.resetPassword(email)
    }
}