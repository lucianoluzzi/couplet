package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel
import com.couplesdating.couplet.domain.network.Response

class AddInviteeIdUseCaseImpl(
    private val inviteRepository: InviteRepository
) : AddInviteeIdUseCase {

    override suspend fun addInviteeId(
        inviteeId: String,
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?
    ): Response {
        val invite = InviteModel(
            inviteId = inviteId,
            inviteeId = inviteeId,
            inviterId = inviterId,
            inviterDisplayName = inviterDisplayName ?: "",
            hasAccepted = false
        )
        return inviteRepository.saveInvite(invite)
    }
}