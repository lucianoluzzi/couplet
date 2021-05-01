package com.couplesdating.couplet.data.repository

import android.content.SharedPreferences
import com.couplesdating.couplet.data.extensions.insert
import com.couplesdating.couplet.data.extensions.observeKey
import com.couplesdating.couplet.domain.model.Response
import com.couplesdating.couplet.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class PairRepositoryImpl(
    private val database: FirebaseFirestore,
    private val preferences: SharedPreferences
) : PairRepository {

    override fun shouldShowSync(): Flow<Boolean> = preferences.observeKey(SHOULD_SHOW_SYNC, true)

    override fun setSyncPageShown() {
        with(preferences.edit()) {
            putBoolean(SHOULD_SHOW_SYNC, false)
            apply()
        }
    }

    override suspend fun formPair(
        firstUserId: String,
        secondUserId: String
    ): Response {
        val pairMap = hashMapOf(
            "user_1" to firstUserId,
            "user_2" to secondUserId
        )

        val taskResponse = database
            .collection("pair")
            .insert(pairMap)

        if (taskResponse.isSuccessful) {
            return Response.Completed
        }

        return Response.Error(taskResponse.exception?.message)
    }

    override suspend fun getPartner(currentUserId: String): Response {
        val firstUserQuery = database.collection("pair")
            .whereEqualTo("user_1", currentUserId)
        val secondUserQuery = database.collection("pair")
            .whereEqualTo("user_2", currentUserId)

        val firstResult = firstUserQuery.get().await()
        val secondResult = secondUserQuery.get().await()
        val mergedResulted = firstResult.documents.map {
            it.get("user_2")?.toString()
        } + secondResult.documents.map {
            it.get("user_1")?.toString()
        }

        val partnerId = mergedResulted.firstOrNull()
        partnerId?.let {
            val document = database.collection("users")
                .document(partnerId).get().await()
            val email = document.get("email")?.toString()
            val id = document.get("id")?.toString() ?: ""
            val displayName = document.get("displayName")?.toString()
            val partner = User(
                userId = id,
                email = email,
                name = displayName
            )
            return Response.Success(partner)
        }

        return Response.Error("Erro!")
    }

    private companion object {
        private const val ACCEPTED_PAIR_INVITE_KEY = "ACCEPTED_PAIR_INVITE"
        private const val SHOULD_SHOW_SYNC = "SHOULD_SHOW_SYNC"
    }
}