package com.couplesdating.couplet.domain.useCase.invite

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.InviteModel
import java.util.*

class SavePairInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : SavePairInviteUseCase {

    override fun savePairInvite(
        inviteId: String,
        inviterId: String,
        inviterDisplayName: String?,
        note: String?,
        hasAccepted: Boolean,
        timestamp: String?
    ) {
        val invite = InviteModel(
            inviteId = inviteId,
            inviterId = inviterId,
            inviterDisplayName = inviterDisplayName ?: "",
            note = note,
            hasAccepted = hasAccepted,
            timestamp = Date()
        )
        inviteRepository.savePairInvite(invite)
    }
}