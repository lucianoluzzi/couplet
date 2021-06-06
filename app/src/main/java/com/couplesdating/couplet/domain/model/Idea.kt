package com.couplesdating.couplet.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Idea(
    val id: String,
    val title: String,
    val description: String
)