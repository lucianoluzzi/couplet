package com.couplesdating.couplet.domain.model

import java.util.*

data class InviteModel(
    val inviterId: String,
    val inviteeId: String? = null,
    val inviterDisplayName: String,
    val inputInviteeDisplayName: String? = null,
    val note: String?,
    val inviteId: String,
    val timestamp: Date? = null,
    val hasAccepted: Boolean = false
)
