package com.couplesdating.couplet.data

import android.util.Log
import com.couplesdating.couplet.data.extensions.register
import com.couplesdating.couplet.data.extensions.resetPassword
import com.couplesdating.couplet.data.extensions.signIn
import com.couplesdating.couplet.data.extensions.updateUser
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class UserRepositoryImpl(
    private val authenticator: FirebaseAuth
) : UserRepository {

    override fun getCurrentUser(): User? {
        authenticator.currentUser?.let {
            val name = it.displayName
            val email = it.email

            if (name == null || email == null)
                return null

            return User(
                name = name,
                email = email
            )
        }

        return null
    }

    override suspend fun signIn(user: User): User? {
        user.password?.let { userPassword ->
            val authResult = authenticator.signIn(user.email, userPassword)
            if (authResult.isSuccessful) {
                Log.d("SignIn", "signInWithEmail:success")

                val authenticatedUser = authenticator.currentUser
                authenticatedUser?.let {
                    val name = it.displayName
                    val email = it.email

                    if (name != null && email != null) {
                        return User(
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

    override suspend fun register(email: String, password: String): Response {
        val authResult = authenticator.register(
            email = email, password = password
        )
        return if (authResult.isSuccessful) {
            Response.Success
        } else {
            Response.Error(authResult.exception?.message)
        }
    }

    override suspend fun updateProfile(userName: String, gender: String): Response {
        authenticator.currentUser?.let { currentUser ->
            val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                .setDisplayName(userName)
                .build()

            val updateResult = currentUser.updateUser(userProfileChangeRequest)
            if (updateResult.isSuccessful) {
                return Response.Success
            }
        }

        return Response.Error()
    }

    override suspend fun resetPassword(email: String): Response {
        val resetPasswordResult = authenticator.resetPassword(email)
        return if (resetPasswordResult.isSuccessful) {
            Response.Success
        } else {
            Response.Error(resetPasswordResult.exception?.message)
        }
    }
}