package com.couplesdating.couplet.data

import android.util.Log
import com.couplesdating.couplet.data.extensions.signIn
import com.couplesdating.couplet.domain.model.User
import com.google.firebase.auth.FirebaseAuth

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
}