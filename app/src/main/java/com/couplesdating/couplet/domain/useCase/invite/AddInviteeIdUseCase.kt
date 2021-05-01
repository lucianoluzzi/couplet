package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.domain.model.Response

interface AddInviteeIdUseCase {
    suspend fun addInviteeId(
        inviteeId: String,
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?,
        note: String?
    ): Response
}