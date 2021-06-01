package com.couplesdating.couplet.data.repository

import android.util.Log
import com.couplesdating.couplet.domain.model.Category
import com.couplesdating.couplet.domain.model.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import kotlinx.coroutines.tasks.await

class CategoryRepositoryImpl(
    private val database: FirebaseFirestore,
    private val service: FirebaseFunctions
) : CategoryRepository {

    override suspend fun getCategories(userId: String): Response {
        val categoryResponse = database.collection("category")
            .get()
            .await()

        val categories = categoryResponse.documents.map {
            Category(
                id = it.get("id").toString(),
                title = it.get("title").toString(),
                description = it.get("description").toString(),
                spiciness = it.getLong("spiciness")?.toInt() ?: 4,
                isPremium = it.getBoolean("is_premium") ?: true,
                hasNewIdeas = false
            )
        }.sortedBy {
            it.spiciness
        }

        getNewIdeas(userId)
        return Response.Success(categories)
    }

    private suspend fun getNewIdeas(userId: String) {
        try {
            val data = hashMapOf(
                "userId" to userId
            )
            val result = service.getHttpsCallable("getIdeas")
                .call(data)
                .await()
            Log.d("GET_NEW_IDEAS", result.data.toString())
        } catch (exception: Exception) {
            Log.d("GET_NEW_IDEAS", exception.message.toString())
        }
    }
}