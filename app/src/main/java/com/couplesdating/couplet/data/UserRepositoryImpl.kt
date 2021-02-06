package com.couplesdating.couplet.data

import com.google.firebase.auth.FirebaseAuth

class UserRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : UserRepository {
}