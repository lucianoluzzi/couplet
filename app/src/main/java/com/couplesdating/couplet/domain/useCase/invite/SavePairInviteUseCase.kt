package com.couplesdating.couplet.domain.useCase.invite

interface SavePairInviteUseCase {
    fun savePairInvite(
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?,
        hasAccepted: Boolean,
        timestamp: String?
    )
}