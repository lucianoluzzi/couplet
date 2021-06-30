package com.couplesdating.couplet.domain.useCase.user

import com.couplesdating.couplet.data.repository.UserRepository
import com.couplesdating.couplet.domain.network.Response

class UpdateUserUseCaseImpl(
    private val userRepository: UserRepository
) : UpdateUserUseCase {

    override suspend fun updateNameAndGender(name: String, gender: String): Response {
        return userRepository.updateProfile(
            userName = name,
            gender = gender
        )
    }

    override suspend fun updateCloudMessageRegistrationToken(registrationToken: String) {
        userRepository.updateProfile(
            registrationToken = registrationToken
        )
    }
}