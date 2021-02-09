package com.couplesdating.couplet.data

import com.couplesdating.couplet.domain.model.User

interface UserRepository {
    fun getCurrentUser(): User?
    suspend fun signIn(user: User): User?
}