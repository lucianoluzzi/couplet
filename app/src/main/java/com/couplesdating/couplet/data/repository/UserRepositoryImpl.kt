package com.couplesdating.couplet.data.repository

import android.util.Log
import com.couplesdating.couplet.data.extensions.register
import com.couplesdating.couplet.data.extensions.resetPassword
import com.couplesdating.couplet.data.extensions.signIn
import com.couplesdating.couplet.data.extensions.updateUser
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.network.Response
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl(
    private val database: FirebaseFirestore,
    private val authenticator: FirebaseAuth
) : UserRepository {

    override suspend fun getCurrentUser(): User? {
        authenticator.currentUser?.let {
            val userId = it.uid
            val name = it.displayName
            val email = it.email

            val userResponse = database.collection("users")
                .whereEqualTo("id", userId)
                .get()
                .await()
            val isPremium = userResponse.map { response ->
                response.getBoolean("is_premium")
            }.firstOrNull() ?: false
            val cloudMessageRegistrationToken = userResponse.map { response ->
                response.getString("cloud_message_registration_token")
            }.firstOrNull()

            return User(
                userId = userId,
                name = name,
                email = email,
                isPremium = isPremium,
                cloudMessageRegistrationToken = cloudMessageRegistrationToken
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
            getCurrentUser()?.let {
                Response.Success(it)
            } ?: Response.Completed
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

    override suspend fun updateProfile(registrationToken: String?) {
        getCurrentUser()?.let { user ->
            val updateData = hashMapOf(
                "is_premium" to user.isPremium,
                "cloud_message_registration_token" to registrationToken,
                "email" to user.email,
                "displayName" to user.name
            )
            database.collection("users")
                .document(user.userId)
                .set(updateData, SetOptions.merge())
                .await()
        }
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