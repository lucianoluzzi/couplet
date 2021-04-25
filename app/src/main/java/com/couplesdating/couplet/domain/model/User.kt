package com.couplesdating.couplet.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: String,
    val email: String?,
    val name: String? = null,
    val password: String? = null,
    val pairedPartner: User? = null
) : Parcelable {
    val firstName = name?.split(" ")?.firstOrNull()
}