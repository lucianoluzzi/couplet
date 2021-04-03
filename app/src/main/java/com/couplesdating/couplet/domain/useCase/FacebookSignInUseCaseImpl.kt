package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.UserRepository
import com.couplesdating.couplet.domain.model.Response
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider


class FacebookSignInUseCaseImpl(
    private val userRepository: UserRepository
) : FacebookSignInUseCase {

    override suspend fun signIn(token: AccessToken): Response {
        val credential = FacebookAuthProvider.getCredential(token.token)
        return userRepository.signIn(credential)
    }
}