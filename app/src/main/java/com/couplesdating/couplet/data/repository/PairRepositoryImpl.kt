package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.data.extensions.insert
import com.couplesdating.couplet.domain.model.Response
import com.google.firebase.firestore.FirebaseFirestore

class PairRepositoryImpl(
    private val database: FirebaseFirestore
) : PairRepository {

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
            return Response.Success
        }

        return Response.Error(taskResponse.exception?.message)
    }
}