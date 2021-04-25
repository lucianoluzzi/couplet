package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.data.repository.UserRepository
import com.couplesdating.couplet.domain.model.Response
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInUseCaseImpl(
    private val userRepository: UserRepository
) : GoogleSignInUseCase {

    override suspend fun signIn(idToken: String, displayName: String): Response {
        val authCredential = GoogleAuthProvider.getCredential(idToken, null)
        val response = userRepository.signIn(authCredential, displayName)

        if (response is Response.Completed) {
            userRepository.updateProfile(displayName)
        }

        return response
    }
}