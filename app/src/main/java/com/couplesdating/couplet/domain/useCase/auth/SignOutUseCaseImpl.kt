package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.data.repository.UserRepository
import com.couplesdating.couplet.domain.network.Response

class SignOutUseCaseImpl(
    private val userRepository: UserRepository
) : SignOutUseCase {

    override suspend fun signOut(): Response {
        return try {
            userRepository.signOut()
            Response.Completed
        } catch (exception: Exception) {
            Response.Error(exception.message)
        }
    }
}