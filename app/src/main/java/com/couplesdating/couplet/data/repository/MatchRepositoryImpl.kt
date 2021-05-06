package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.model.Idea
import com.couplesdating.couplet.domain.model.Match
import com.couplesdating.couplet.domain.model.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MatchRepositoryImpl(
    private val database: FirebaseFirestore
) : MatchRepository {

    override suspend fun getNewMatches(currentUserId: String): Response {
        val firstUserQuery = database.collection("match")
            .whereEqualTo("user_1_id", currentUserId)
            .whereEqualTo("user_1_has_seen", false)

        val secondUserQuery = database.collection("match")
            .whereEqualTo("user_2_id", currentUserId)
            .whereEqualTo("user_2_has_seen", false)

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