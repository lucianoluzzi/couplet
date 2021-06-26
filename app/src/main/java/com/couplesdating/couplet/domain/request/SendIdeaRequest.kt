package com.couplesdating.couplet.domain.request

import com.couplesdating.couplet.domain.model.UserResponse
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendIdeaRequest(
    @Json(name = "idea_id")
    val ideaId: String,
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "user_response")
    val userResponse: UserResponse
)
