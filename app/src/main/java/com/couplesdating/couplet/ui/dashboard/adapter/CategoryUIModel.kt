package com.couplesdating.couplet.ui.dashboard.adapter

import android.os.Parcelable
import com.couplesdating.couplet.domain.model.Idea
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryUIModel(
    val id: String,
    val title: String,
    val description: String,
    val isPremium: Boolean,
    val spiciness: Int,
    val imageSmall: Int,
    val imageBig: Int,
    val ideas: List<Idea>,
    val hasNewIdeas: Boolean
) : Parcelable
