package com.couplesdating.couplet.domain.useCase.auth

import com.couplesdating.couplet.domain.model.Response
import com.google.firebase.auth.AuthCredential

interface GoogleSignInUseCase {
    suspend fun signIn(idToken: String, displayName: String): Response
}