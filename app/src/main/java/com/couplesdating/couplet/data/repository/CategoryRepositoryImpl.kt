package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Category
import com.couplesdating.couplet.domain.model.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CategoryRepositoryImpl(
    private val database: FirebaseFirestore
) : CategoryRepository {

    override suspend fun getCategories(): Response {
        val categoryResponse = database.collection("category")
            .get()
            .await()

        val categories = categoryResponse.documents.map {
            Category(
                id = it.get("id").toString(),
                title = it.get("title").toString(),
                description = it.get("description").toString(),
                spiciness = it.getLong("spiciness")?.toInt() ?: 4,
                hasNewIdeas = false
            )
        }.sortedBy {
            it.spiciness
        }

        return Response.Success(categories)
    }
}