package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.network.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class MatchRepositoryImpl(
    private val database: FirebaseFirestore
) : MatchRepository {

    override suspend fun getNewMatches(currentUserId: String): Response {
        val firstUserQuery = database.collection("match")
            .whereEqualTo("user_1_id", currentUserId)
            .orderBy("creation_date", Query.Direction.DESCENDING)

        val secondUserQuery = database.collection("match")
            .whereEqualTo("user_2_id", currentUserId)
            .orderBy("creation_date", Query.Direction.DESCENDING)

        val firstResult = firstUserQuery.get().await()
        val secondResult = secondUserQuery.get().await()

        val mergedResults = firstResult.documents.map {
            getIdea(it.get("idea_id").toString())?.let { idea ->
                Match(
                    id = it.get("id").toString(),
                    partnerResponse = it.get("user_2_response").toString(),
                    idea = idea
                )
            }
        } + secondResult.documents.map {
            getIdea(it.get("idea_id").toString())?.let { idea ->
                Match(
                    id = it.get("id").toString(),
                    partnerResponse = it.get("user_1_response").toString(),
                    idea = idea
                )
            }
        }

        return Response.Success(mergedResults)
    }

    override suspend fun deleteAllMatches(currentUserId: String): Response {
        try {
            val firstUserQuery = database.collection("match")
                .whereEqualTo("user_1_id", currentUserId)
            val secondUserQuery = database.collection("match")
                .whereEqualTo("user_2_id", currentUserId)

            val firstResult = firstUserQuery.get().await()
            val secondResult = secondUserQuery.get().await()

            val writeBatch = database.batch()
            val mergedResults = firstResult.documents + secondResult.documents
            mergedResults.forEach { documentSnapshot ->
                writeBatch.delete(documentSnapshot.reference)
            }
            writeBatch.commit().await()
            return Response.Completed
        } catch (exception: Exception) {
            return Response.Error(exception.toString())
        }
    }

    override suspend fun deleteMatch(matchId: String): Response {
        return try {
            val result = database.collection("match")
                .whereEqualTo("id", matchId)
                .get()
                .await()
            result.documents.firstOrNull()?.let {
                database.collection("match")
                    .document(it.id)
                    .delete()
                    .await()
            }
            Response.Completed
        } catch (exception: Exception) {
            Response.Error(exception.toString())
        }
    }

    private suspend fun getIdea(ideaId: String): Idea? {
        val ideaResponse = database.collection("idea")
            .whereEqualTo("id", ideaId)
            .get()
            .await()

        return ideaResponse.documents.map {
            Idea(
                id = it.get("id").toString(),
                title = it.get("title").toString(),
                description = it.get("description").toString()
            )
        }.firstOrNull()
    }
}