package com.couplesdating.couplet.domain.model

data class Category(
    val id: String,
    val title: String,
    val description: String,
    val isPremium: Boolean = false,
    val spiciness: Int,
    val hasNewIdeas: Boolean
)