package com.couplesdating.couplet.ui.invite

data class InviteModel(
    val userId: String,
    val currentUserDisplay: String,
    val displayName: String,
    val note: String?,
    val inviteId: String
)
