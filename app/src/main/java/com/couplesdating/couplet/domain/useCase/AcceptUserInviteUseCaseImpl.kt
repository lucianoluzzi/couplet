package com.couplesdating.couplet.domain.useCase

import com.couplesdating.couplet.data.repository.PairRepository
import com.couplesdating.couplet.domain.model.AcceptedInvite

class AcceptUserInviteUseCaseImpl(
    private val pairRepository: PairRepository
) : AcceptUserInviteUseCase {

    override fun acceptUserInvite(
        inviteId: String,
        userId: String
    ) {
        val acceptedInvite = AcceptedInvite(
            inviteId = inviteId,
            userId = userId
        )
        pairRepository.saveAcceptedPairInvite(acceptedInvite)
    }
}