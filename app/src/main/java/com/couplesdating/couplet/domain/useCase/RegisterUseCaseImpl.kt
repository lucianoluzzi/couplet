package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.UserRepository
import com.couplesdating.couplet.domain.model.Response

class RegisterUseCaseImpl(
    private val userRepository: UserRepository
) : RegisterUseCase {

    override suspend fun register(
        email: String,
        password: String,
        name: String,
        gender: String
    ): Response {
        val registerResponse = userRepository.register(
            email = email,
            password = password
        )
        if (registerResponse is Response.Success<*>) {
            userRepository.updateProfile(
                userName = name,
                gender = gender
            )
        }

        return registerResponse
    }
}