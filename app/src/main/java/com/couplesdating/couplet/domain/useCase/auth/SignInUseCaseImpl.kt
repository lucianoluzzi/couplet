package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.data.repository.UserRepository
import com.couplesdating.couplet.domain.model.User

class SignInUseCaseImpl(
    private val userRepository: UserRepository
) : SignInUseCase {

    override suspend fun signIn(email: String, password: String): User? {
        val user = User(
            userId = "",
            email = email,
            password = password
        )
        return userRepository.signIn(user)
    }
}