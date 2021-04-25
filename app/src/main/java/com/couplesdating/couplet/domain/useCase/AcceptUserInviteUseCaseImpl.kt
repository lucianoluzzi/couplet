package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.InviteRepository
import com.couplesdating.couplet.domain.model.AcceptedInvite

class AcceptUserInviteUseCaseImpl(
    private val inviteRepository: InviteRepository
) : AcceptUserInviteUseCase {

    override fun acceptUserInvite(
        inviteId: String,
        userId: String
    ) {
        val acceptedInvite = AcceptedInvite(
            inviteId = inviteId,
            userId = userId
        )
        inviteRepository.saveAcceptedPairInvite(acceptedInvite)
    }
}