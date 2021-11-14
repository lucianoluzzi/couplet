package com.couplesdating.couplet.domain.useCase.user

import com.couplesdating.couplet.data.repository.UserRepository
import com.couplesdating.couplet.domain.network.Response

class DeleteUserUseCaseImpl(
    private val userRepository: UserRepository
) : DeleteUserUseCase {

    override suspend fun deleteUser(userId: String): Response {
        return userRepository.deleteUser(userId)
    }
}