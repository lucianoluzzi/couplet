package com.couplesdating.couplet.ui.dashboard.adapter

data class CategoryUIModel(
    val id: String,
    val title: String,
    val description: String,
    val isPremium: Boolean,
    val spiciness: Int,
    val image: Int,
    val hasNewIdeas: Boolean
)
