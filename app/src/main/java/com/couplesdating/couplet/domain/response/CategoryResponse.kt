package com.couplesdating.couplet.domain.response

import com.couplesdating.couplet.domain.model.Idea
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryResponse(
    val id: String,
    val title: String,
    val description: String,
    @Json(name = "is_premium")
    val isPremium: Boolean = false,
    val spiciness: Int,
    val ideas: List<Idea>
)