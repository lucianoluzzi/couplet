package com.couplesdating.couplet.ui.dashboard.adapter

import com.couplesdating.couplet.domain.model.Idea

data class CategoryUIModel(
    val id: String,
    val title: String,
    val description: String,
    val isPremium: Boolean,
    val spiciness: Int,
    val image: Int,
    val ideas: List<Idea>,
    val hasNewIdeas: Boolean
)
