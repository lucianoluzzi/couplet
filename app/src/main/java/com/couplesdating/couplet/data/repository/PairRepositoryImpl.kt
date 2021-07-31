package com.couplesdating.couplet.data.repository

import android.content.SharedPreferences
import com.couplesdating.couplet.data.extensions.observeKey
import com.couplesdating.couplet.domain.model.User
import com.couplesdating.couplet.domain.network.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PairRepositoryImpl(
    private val database: FirebaseFirestore,
    private val preferences: SharedPreferences,
    private val service: FirebaseFunctions
) : PairRepository {

    override fun shouldShowSync(): Flow<Boolean> = preferences.observeKey(SHOULD_SHOW_SYNC, true)

    override fun setSyncPageShown() {
        with(preferences.edit()) {
            putBoolean(SHOULD_SHOW_SYNC, false)
            apply()
        }
    }

    override suspend fun formPair(
        inviterId: String,
        currentUserId: String
    ): Response = withContext(Dispatchers.IO) {
        val pairMap = hashMapOf(
            "inviterId" to inviterId,
            "inviteeId" to currentUserId
        )

        return@withContext try {
            service.getHttpsCallable("formPair")
                .call(pairMap)
                .await()
            Response.Success<Unit>()
        } catch (exception: Exception) {
            Response.Error(exception.message)
        }
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

        return Response.Error("No partner found")
    }

    private companion object {
        private const val ACCEPTED_PAIR_INVITE_KEY = "ACCEPTED_PAIR_INVITE"
        private const val SHOULD_SHOW_SYNC = "SHOULD_SHOW_SYNC"
    }
}