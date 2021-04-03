package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User
import com.google.firebase.auth.AuthCredential

interface UserRepository {
    fun getCurrentUser(): User?
    suspend fun signIn(user: User): User?
    suspend fun signIn(authCredential: AuthCredential, displayName: String? = null): Response
    suspend fun register(email: String, password: String): Response
    suspend fun updateProfile(userName: String, gender: String? = null): Response
    suspend fun resetPassword(email: String): Response
}