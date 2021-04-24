package com.couplesdating.couplet.domain.model

data class InviteModel(
    val userId: String,
    val currentUserDisplay: String,
    val displayName: String,
    val note: String?,
    val inviteId: String
)
