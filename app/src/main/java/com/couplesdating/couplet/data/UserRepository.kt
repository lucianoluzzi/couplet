package com.couplesdating.couplet.data

import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User

interface UserRepository {
    fun getCurrentUser(): User?
    suspend fun signIn(user: User): User?
    suspend fun register(email: String, password: String): Response
    suspend fun updateProfile(userName: String, gender: String): Response
    suspend fun resetPassword(email: String): Response
}