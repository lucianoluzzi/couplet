package com.couplesdating.couplet.data.repository

import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.request.SendIdeaRequest
import com.google.firebase.functions.FirebaseFunctions
import com.squareup.moshi.Moshi
import kotlinx.coroutines.tasks.await

class IdeaRepositoryImpl(
    private val service: FirebaseFunctions
) : IdeaRepository {

    override suspend fun sendIdeaResponse(sendIdeaRequest: SendIdeaRequest): Response {
        return try {
            val requestJson = getIdeaRequestJson(sendIdeaRequest)
            doCall(requestJson)
        } catch (exception: Exception) {
            Response.Error(exception.message)
        }
    }

    private fun getIdeaRequestJson(ideaRequest: SendIdeaRequest): String {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(SendIdeaRequest::class.java)
        return jsonAdapter.toJson(ideaRequest)
    }

    private suspend fun doCall(requestJson: String): Response {
        val data = hashMapOf(
            "ideaResponse" to requestJson
        )
        return try {
            service.getHttpsCallable("sendIdeaResponse")
                .call(data)
                .await()
            Response.Success<Unit>()
        } catch (exception: Exception) {
            Response.Error(exception.message)
        }
    }
}