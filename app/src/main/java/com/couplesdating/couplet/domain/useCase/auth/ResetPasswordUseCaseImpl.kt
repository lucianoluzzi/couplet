package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.data.repository.UserRepository
import com.couplesdating.couplet.domain.network.Response

class ResetPasswordUseCaseImpl(
    private val userRepository: UserRepository
) : ResetPasswordUseCase {

    override suspend fun resetPassword(email: String): Response {
        return userRepository.resetPassword(email)
    }
}