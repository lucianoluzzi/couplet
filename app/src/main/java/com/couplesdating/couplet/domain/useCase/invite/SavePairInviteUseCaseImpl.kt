package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel

class SavePairInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : SavePairInviteUseCase {

    override fun savePairInvite(
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?,
        note: String?,
        hasAccepted: Boolean
    ) {
        val invite = InviteModel(
            inviteId = inviteId,
            inviterId = inviterId,
            inviterDisplayName = inviterDisplayName ?: "",
            note = note,
            hasAccepted = hasAccepted
        )
        inviteRepository.savePairInvite(invite)
    }
}