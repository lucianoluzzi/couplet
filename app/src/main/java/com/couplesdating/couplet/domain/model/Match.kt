package com.couplesdating.couplet.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Match(
    val id: String,
    val idea: Idea,
    val partnerResponse: String
) : Parcelable