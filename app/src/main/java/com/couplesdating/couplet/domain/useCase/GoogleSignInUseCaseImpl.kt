package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.UserRepository
import com.couplesdating.couplet.domain.model.Response
import com.google.firebase.auth.AuthCredential

class GoogleSignInUseCaseImpl(
    private val userRepository: UserRepository
) : GoogleSignInUseCase {

    override suspend fun signIn(authCredential: AuthCredential, displayName: String): Response {
        val response = userRepository.signIn(authCredential, displayName)

        if (response is Response.Success) {
            userRepository.updateProfile(displayName)
        }

        return response
    }
}