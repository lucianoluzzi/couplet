package com.couplesdating.couplet.data

import com.couplesdating.couplet.domain.model.Response
import com.google.firebase.firestore.FirebaseFirestore

class PairRepositoryImpl(
    private val fireStoreDatabase: FirebaseFirestore
) : PairRepository {
    override suspend fun formPair(firstUserId: String, secondUserId: String): Response {
        TODO("Not yet implemented")
    }
}