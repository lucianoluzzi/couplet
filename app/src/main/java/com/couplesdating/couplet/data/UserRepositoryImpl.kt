package com.couplesdating.couplet.data

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
}