package com.couplesdating.couplet.domain.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Idea(
    val id: String,
    val title: String? = null,
    val description: String,
    @Json(name = "category_id") val categoryId: String
) : Parcelable