package com.couplesdating.couplet.data.repository

import android.util.Log
import com.couplesdating.couplet.data.extensions.register
import com.couplesdating.couplet.data.extensions.resetPassword
import com.couplesdating.couplet.data.extensions.signIn
import com.couplesdating.couplet.data.extensions.updateUser
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class UserRepositoryImpl(
    private val authenticator: FirebaseAuth
) : UserRepository {

    override fun getCurrentUser(): User? {
        authenticator.currentUser?.let {
            val userId = it.uid
            val name = it.displayName
            val email = it.email

            return User(
                userId = userId,
                name = name,
                email = email
            )
        }

        return null
    }

    override suspend fun signIn(user: User): User? {
        if (user.email != null && user.password != null) {
            val authResult = authenticator.signIn(user.email, user.password)
            if (authResult.isSuccessful) {
                Log.d("SignIn", "signInWithEmail:success")

                val authenticatedUser = authenticator.currentUser
                authenticatedUser?.let {
                    val userId = it.uid
                    val name = it.displayName
                    val email = it.email

                    if (name != null && email != null) {
                        return User(
                            userId = userId,
                            name = name,
                            email = email
                        )
                    }
                }
            } else {
                return null
            }
        }

        return null
    }

    override suspend fun signIn(authCredential: AuthCredential, displayName: String?): Response {
        val signInResult = authenticator.signIn(authCredential)
        return if (signInResult.isSuccessful) {
            Response.Completed
        } else {
            Response.Error(signInResult.exception?.message)
        }
    }

    override suspend fun register(email: String, password: String): Response {
        val authResult = authenticator.register(
            email = email, password = password
        )
        return if (authResult.isSuccessful) {
            Response.Completed
        } else {
            Response.Error(authResult.exception?.message)
        }
    }

    override suspend fun updateProfile(userName: String, gender: String?): Response {
        authenticator.currentUser?.let { currentUser ->
            val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build()

            val updateResult = currentUser.updateUser(userProfileChangeRequest)
            if (updateResult.isSuccessful) {
                return Response.Completed
            }
        }

        return Response.Error()
    }

    override suspend fun resetPassword(email: String): Response {
        val resetPasswordResult = authenticator.resetPassword(email)
        return if (resetPasswordResult.isSuccessful) {
            Response.Completed
        } else {
            Response.Error(resetPasswordResult.exception?.message)
        }
    }
}