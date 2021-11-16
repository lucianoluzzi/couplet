package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.model.User
import com.google.firebase.auth.AuthCredential

interface UserRepository {
    suspend fun getCurrentUser(): User?
    suspend fun signOut()
    suspend fun clearUserData()
    suspend fun signIn(user: User): User?
    suspend fun signIn(authCredential: AuthCredential, displayName: String? = null): Response
    suspend fun register(email: String, password: String): Response
    suspend fun updateProfile(userName: String, gender: String? = null): Response
    suspend fun updateProfile(registrationToken: String? = null)
    suspend fun resetPassword(email: String): Response
    suspend fun deleteUser(userId: String): Response
}