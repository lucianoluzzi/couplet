package com.couplesdating.couplet.data.dataSource

import android.util.Log
import com.couplesdating.couplet.domain.network.Response
import com.couplesdating.couplet.domain.response.CategoriesIdeasResponse
import com.google.firebase.functions.FirebaseFunctions
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CategoryRemoteDataSource(
    private val service: FirebaseFunctions
) {
    suspend fun getCategories(userId: String, timeZone: String): Response =
        withContext(Dispatchers.IO) {
            return@withContext getNewIdeas(userId, timeZone)
        }

    private suspend fun getNewIdeas(userId: String, timeZone: String): Response {
        return try {
            val resultJson = doCall(userId, timeZone)
            Log.d("GET_NEW_IDEAS", resultJson)
            val categories = getCategoriesResponseFromJSON(resultJson)
            return Response.Success(categories?.categoriesIdeas ?: emptyList())
        } catch (exception: Exception) {
            Log.d("GET_NEW_IDEAS", exception.message.toString())
            Response.Error(exception.message)
        }
    }

    private fun getCategoriesResponseFromJSON(resultJson: String): CategoriesIdeasResponse? {
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter(CategoriesIdeasResponse::class.java)
        return jsonAdapter.fromJson(resultJson)
    }

    private suspend fun doCall(userId: String, timeZone: String): String {
        val data = hashMapOf(
            "userId" to userId,
            "timeZoneId" to timeZone
        )
        val result = service.getHttpsCallable("getIdeas")
            .call(data)
            .await()
        return result.data.toString()
    }
}