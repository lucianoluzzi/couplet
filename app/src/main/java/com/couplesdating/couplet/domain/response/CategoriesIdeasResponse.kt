package com.couplesdating.couplet.domain.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesIdeasResponse(
    @Json(name = "categories_ideas")
    val categoriesIdeas: List<CategoryResponse>
)